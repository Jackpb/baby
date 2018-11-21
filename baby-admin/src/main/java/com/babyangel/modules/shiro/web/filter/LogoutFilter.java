package com.babyangel.modules.shiro.web.filter;

import com.babyangel.common.constant.BabyConstants;
import com.babyangel.common.constant.ShiroConstants;
import com.babyangel.common.utils.MessageUtils;
import com.babyangel.common.utils.SpringContextUtils;
import com.babyangel.common.utils.StringUtils;
import com.babyangel.common.utils.security.ShiroUtils;
import com.babyangel.modules.manager.AsyncManager;
import com.babyangel.modules.manager.factory.AsyncFactory;
import com.babyangel.modules.monitor.entity.OnlineSession;
import com.babyangel.modules.monitor.service.SysUserOnlineService;
import com.babyangel.modules.shiro.session.OnlineSessionDAO;
import com.babyangel.modules.sys.entity.SysUserEntity;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 退出过滤器
 * 
 * @author ruoyi
 */
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter
{
    private static final Logger log = LoggerFactory.getLogger(LogoutFilter.class);

    /**
     * 退出后重定向的地址
     */
    private String loginUrl;

    public String getLoginUrl()
    {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl)
    {
        this.loginUrl = loginUrl;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception
    {
        try
        {
            Subject subject = getSubject(request, response);
            String redirectUrl = getRedirectUrl(request, response, subject);
            try
            {
                SysUserEntity user = ShiroUtils.getUserEntity();
                if (StringUtils.isNotNull(user))
                {
                    String loginName = user.getUsername();
                    // 记录用户退出日志
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, BabyConstants.LOGOUT, MessageUtils.message("user.logout.success")));

                }
                // 退出登录
                subject.logout();
            }
            catch (SessionException ise)
            {
                log.error("logout fail.", ise);
            }
            issueRedirect(request, response, redirectUrl);
        }
        catch (Exception e)
        {
            log.error("Encountered session exception during logout.  This can generally safely be ignored.", e);
        }
        return false;
    }

    /**
     * 退出跳转URL
     */
    @Override
    protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject)
    {
        String url = getLoginUrl();
        if (StringUtils.isNotEmpty(url))
        {
            return url;
        }
        return super.getRedirectUrl(request, response, subject);
    }

}
