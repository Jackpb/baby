package com.babyangel.modules.shiro.service;

import com.babyangel.common.constant.BabyConstants;
import com.babyangel.common.constant.ShiroConstants;
import com.babyangel.common.constant.UserConstants;
import com.babyangel.common.exception.user.*;
import com.babyangel.common.utils.DateUtils;
import com.babyangel.common.utils.MessageUtils;
import com.babyangel.common.utils.ServletUtils;
import com.babyangel.common.utils.security.ShiroUtils;
import com.babyangel.modules.manager.AsyncManager;
import com.babyangel.modules.manager.factory.AsyncFactory;
import com.babyangel.modules.sys.entity.SysUserEntity;
import com.babyangel.modules.sys.entity.UserStatus;
import com.babyangel.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 登录校验方法
 * 
 * @author ruoyi
 */
@Component
public class LoginService
{
    @Autowired
    private PasswordService passwordService;

    @Autowired
    private SysUserService userService;

    /**
     * 登录
     */
    public SysUserEntity login(String username, String password)
    {
        // 验证码校验
//        if (!StringUtils.isEmpty(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA)))
//        {
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, BabyConstants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
//            throw new CaptchaException();
//        }
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, BabyConstants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotEmptyException();
        }
//        // 密码如果不在指定范围内 错误
//        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
//                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
//        {
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, BabyConstants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
//            throw new UserPasswordNotMatchException();
//        }
//
//        // 用户名不在指定范围内 错误
//        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
//                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
//        {
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, BabyConstants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
//            throw new UserPasswordNotMatchException();
//        }

        // 查询用户信息
        SysUserEntity user = userService.selectByUserName(username);

        if (user == null && maybeMobilePhoneNumber(username))
        {//使用电话号码登录
            user = userService.selectUserByPhoneNumber(username);
        }

        if (user == null && maybeEmail(username))
        {//使用email登录
            user = userService.selectUserByEmail(username);
        }

        if (user == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, BabyConstants.LOGIN_FAIL, MessageUtils.message("user.not.exists")));
            throw new UserNotExistsException();
        }
        
//        if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
//        {
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, BabyConstants.LOGIN_FAIL, MessageUtils.message("user.password.delete")));
//            throw new UserDeleteException();
//        }
        String disableStr = UserStatus.DISABLE.getCode();
        String userStatus = user.getStatus().toString();
        if (disableStr.equals(userStatus))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, BabyConstants.LOGIN_FAIL, MessageUtils.message("user.blocked", "账号已被锁定,请联系管理员")));
            throw new UserBlockedException("账号已被锁定,请联系管理员");
        }

        passwordService.validate(user, password);

        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, BabyConstants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
//        recordLoginInfo(user);
        return user;
    }

    private boolean maybeEmail(String username)
    {
        if (!username.matches(UserConstants.EMAIL_PATTERN))
        {
            return false;
        }
        return true;
    }

    private boolean maybeMobilePhoneNumber(String username)
    {
        if (!username.matches(UserConstants.MOBILE_PHONE_NUMBER_PATTERN))
        {
            return false;
        }
        return true;
    }

    /**
     * 记录登录信息
     */
//    public void recordLoginInfo(SysUserEntity user)
//    {
//        user.setLoginIp(ShiroUtils.getIp());
//        user.setLoginDate(DateUtils.getNowDate());
//        userService.updateUserInfo(user);
//    }

}
