package com.babyangel.modules.shiro.web.filter;

import com.babyangel.common.constant.ShiroConstants;
import com.babyangel.common.utils.SpringContextUtils;
import com.babyangel.common.utils.security.ShiroUtils;
import com.babyangel.modules.monitor.entity.OnlineSession;
import com.babyangel.modules.shiro.session.OnlineSessionDAO;
import com.babyangel.modules.sys.entity.SysUserEntity;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 自定义访问控制
 * 
 * @author ruoyi
 */
public class OnlineSessionFilter extends AccessControlFilter
{
    /**
     * 强制退出后重定向的地址
     */
    private String loginUrl="/login.html";

    /**
     * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception
    {
        Subject subject = getSubject(request, response);
        if (subject == null || subject.getSession() == null)
        {
            return true;
        }
        OnlineSessionDAO onlineSessionDAO = (OnlineSessionDAO)SpringContextUtils.getBean("onlineSessionDAO");
        Session session = onlineSessionDAO.readSession(subject.getSession().getId());

        if (session != null && session instanceof OnlineSession)
        {
            OnlineSession onlineSession = (OnlineSession) session;
            request.setAttribute(ShiroConstants.ONLINE_SESSION, onlineSession);
            // 把user对象设置进去
            boolean isGuest = onlineSession.getUserId() == null || onlineSession.getUserId() == 0L;
            if (isGuest == true)
            {
                SysUserEntity user = ShiroUtils.getUserEntity();
                if (user != null)
                {
                    onlineSession.setUserId(user.getUserId());
                    onlineSession.setLoginName(user.getUsername());
                    onlineSession.setDeptName(user.getDeptName());
                    onlineSession.markAttributeChanged();
                }else{
                    saveRequestAndRedirectToLogin(request, response);
                }
            }

            if (onlineSession.getStatus() == OnlineSession.OnlineStatus.off_line)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception
    {
        Subject subject = getSubject(request, response);
        if (subject != null)
        {
            subject.logout();
        }
        saveRequestAndRedirectToLogin(request, response);
        return true;
    }

    // 跳转到登录页
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException
    {
        WebUtils.issueRedirect(request, response, loginUrl);
    }

    /**
     * 同步会话数据到DB 一次请求最多同步一次 防止过多处理 需要放到Shiro过滤器之前
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception
    {
        if(this.isAccessAllowed(request,response,null)||this.onAccessDenied(request,response)){
            OnlineSession session = (OnlineSession) request.getAttribute(ShiroConstants.ONLINE_SESSION);
            // 如果session stop了 也不同步
            // session停止时间，如果stopTimestamp不为null，则代表已停止
            if (session != null && session.getUserId() != null && session.getStopTimestamp() == null)
            {
                OnlineSessionDAO onlineSessionDAO = (OnlineSessionDAO) SpringContextUtils.getBean("onlineSessionDAO");
                onlineSessionDAO.syncToDb(session);
            }
        }

        return true;
    }
}
