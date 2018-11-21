package com.babyangel.modules.shiro.service;

import com.babyangel.common.constant.BabyConstants;
import com.babyangel.common.exception.user.UserPasswordNotMatchException;
import com.babyangel.common.exception.user.UserPasswordRetryLimitExceedException;
import com.babyangel.common.utils.MessageUtils;
import com.babyangel.common.utils.security.ShiroUtils;
import com.babyangel.modules.manager.AsyncManager;
import com.babyangel.modules.manager.factory.AsyncFactory;
import com.babyangel.modules.sys.entity.SysUserEntity;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 登录密码方法
 * 
 * @author ruoyi
 */
@Component
public class PasswordService
{

    @Autowired
    private CacheManager cacheManager;

    private Cache<String, AtomicInteger> loginRecordCache;

    @Value(value = "${user.password.maxRetryCount}")
    private String maxRetryCount;

    @PostConstruct
    public void init()
    {
        loginRecordCache = cacheManager.getCache("loginRecordCache");
    }

    public void validate(SysUserEntity user, String password)
    {
        String loginName = user.getUsername();

        AtomicInteger retryCount = loginRecordCache.get(loginName);

        if (retryCount == null)
        {
            retryCount = new AtomicInteger(0);
            loginRecordCache.put(loginName, retryCount);
        }
        if (retryCount.incrementAndGet() > Integer.valueOf(maxRetryCount).intValue())
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, BabyConstants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount)));
            throw new UserPasswordRetryLimitExceedException(Integer.valueOf(maxRetryCount).intValue());
        }

        if (!matches(user, password))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, BabyConstants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.count", retryCount, password)));
            loginRecordCache.put(loginName, retryCount);
            throw new UserPasswordNotMatchException();
        }
        else
        {
            clearLoginRecordCache(loginName);
        }
    }

    public boolean matches(SysUserEntity user, String newPassword)
    {
        String pwd = user.getPassword();
        String validPwd = ShiroUtils.sha256(newPassword, user.getSalt());
        if(pwd.equals(validPwd)){
            return true;
        }else {
            return false;
        }
//        return user.getPassword().equals(ShiroUtils.sha256(newPassword, user.getSalt()));
    }

    public void clearLoginRecordCache(String username)
    {
        loginRecordCache.remove(username);
    }

}
