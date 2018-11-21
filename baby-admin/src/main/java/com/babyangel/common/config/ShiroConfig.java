/**
 * Copyright 2018 BabyAngel365
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.babyangel.common.config;


import com.babyangel.common.utils.StringUtils;
import com.babyangel.common.utils.security.ShiroSessionListener;
import com.babyangel.modules.shiro.realm.MyUserRealm;
import com.babyangel.modules.shiro.session.OnlineSessionDAO;
import com.babyangel.modules.shiro.session.OnlineSessionFactory;
import com.babyangel.modules.shiro.web.filter.LogoutFilter;
import com.babyangel.modules.shiro.web.filter.OnlineSessionFilter;
import com.babyangel.modules.shiro.web.filter.SyncOnlineSessionFilter;
import com.babyangel.modules.shiro.web.session.OnlineWebSessionManager;
import com.babyangel.modules.shiro.web.session.SpringSessionValidationScheduler;
import com.babyangel.modules.sys.shiro.RedisShiroSessionDAO;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro的配置文件
 *
 * @author Mark sunlightcs@gmail.com
 * @since 3.0.0 2017-09-27
 */
@Configuration
public class ShiroConfig {
    @Value("${babyangel.redis.open}")
    private boolean redisOpen;
    @Value("${babyangel.shiro.redis}")
    private boolean shiroRedis;

    // Session超时时间，单位为毫秒（默认30分钟）
//    @Value("${shiro.session.expireTime}")
    private int expireTime=30;

    // 相隔多久检查一次session的有效性，单位毫秒，默认就是10分钟
//    @Value("${shiro.session.validationInterval}")
    private int validationInterval =10;

//    // 验证码开关
//    @Value("${shiro.user.captchaEnabled}")
//    private boolean captchaEnabled;

//    // 验证码类型
//    @Value("${shiro.user.captchaType}")
//    private String captchaType;

    // 设置Cookie的域名
    @Value("${shiro.cookie.domain}")
    private String domain;

    // 设置cookie的有效访问路径
    @Value("${shiro.cookie.path}")
    private String path;

    // 设置HttpOnly属性
    @Value("${shiro.cookie.httpOnly}")
    private boolean httpOnly;

    // 设置Cookie的过期时间，秒为单位
    @Value("${shiro.cookie.maxAge}")
    private int maxAge;

    // 登录地址
//    @Value("${shiro.user.loginUrl}")
    private String loginUrl="/login.html";

    // 权限认证失败地址
//    @Value("${shiro.user.unauthorizedUrl}")
    private String unauthorizedUrl="/";

    /**
     * 缓存管理器 使用Ehcache实现
     */
    @Bean
    public EhCacheManager getEhCacheManager()
    {
        net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.getCacheManager("babyangel");
        EhCacheManager em = new EhCacheManager();
        if (StringUtils.isNull(cacheManager))
        {
            em.setCacheManagerConfigFile("classpath:ehcache/ehcache-shiro.xml");
            return em;
        }
        else
        {
            em.setCacheManager(cacheManager);
            return em;
        }
    }

    /**
     * 自定义sessionDAO会话
     */
    @Bean("onlineSessionDAO")
    public OnlineSessionDAO sessionDAO()
    {
        OnlineSessionDAO sessionDAO = new OnlineSessionDAO();
        return sessionDAO;
    }

    /**
     * 自定义sessionFactory会话
     */
    @Bean
    public OnlineSessionFactory sessionFactory()
    {
        OnlineSessionFactory sessionFactory = new OnlineSessionFactory();
        return sessionFactory;
    }

    /**
     * 会话管理器
     */
    @Bean
    public OnlineWebSessionManager sessionValidationManager()
    {
        OnlineWebSessionManager manager = new OnlineWebSessionManager();
        // 加入缓存管理器
        manager.setCacheManager(getEhCacheManager());
        // 删除过期的session
        manager.setDeleteInvalidSessions(true);
        // 设置全局session超时时间
        manager.setGlobalSessionTimeout(expireTime * 60 * 1000);
        // 去掉 JSESSIONID
        manager.setSessionIdUrlRewritingEnabled(false);
        // 是否定时检查session
        manager.setSessionValidationSchedulerEnabled(true);
        // 自定义SessionDao
        manager.setSessionDAO(sessionDAO());
        // 自定义sessionFactory
        manager.setSessionFactory(sessionFactory());
        return manager;
    }

    /**
     * 自定义sessionFactory调度器
     */
    @Bean
    public SpringSessionValidationScheduler sessionValidationScheduler()
    {
        SpringSessionValidationScheduler sessionValidationScheduler = new SpringSessionValidationScheduler();
        // 相隔多久检查一次session的有效性，单位毫秒，默认就是10分钟
        sessionValidationScheduler.setSessionValidationInterval(10 * 60 * 1000);
        // 设置会话验证调度器进行会话验证时的会话管理器
        sessionValidationScheduler.setSessionManager(sessionValidationManager());
        return sessionValidationScheduler;
    }

    @Bean("sessionManager")
    public OnlineWebSessionManager sessionManager(){

        OnlineWebSessionManager sessionManager = new OnlineWebSessionManager();


        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        //配置监听
        listeners.add(sessionListener());
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionIdCookie(sessionIdCookie());

        sessionManager.setCacheManager(getEhCacheManager());


        //设置session过期时间为1小时(单位：毫秒)，默认为30分钟
        sessionManager.setGlobalSessionTimeout(60 * 60 * 1000);
//        sessionManager.setGlobalSessionTimeout(expireTime * 60 * 1000);
        //是否开启删除无效的session对象  默认为true
        sessionManager.setDeleteInvalidSessions(true);
        //是否开启定时调度器进行检测过期session 默认为true
        sessionManager.setSessionValidationSchedulerEnabled(true);
        // 去掉 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        // 定义要使用的无效的Session定时调度器
        sessionManager.setSessionValidationScheduler(sessionValidationScheduler());


        //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时(3600000ms)
        //设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
        sessionManager.setSessionValidationInterval(1000*60);//1分钟扫描一次


        //如果开启redis缓存且babyangel.shiro.redis=true，则shiro session存到redis里
//        if(redisOpen && shiroRedis){
//            sessionManager.setSessionDAO(redisShiroSessionDAO());
//        }

        sessionManager.setSessionDAO(sessionDAO());
        // 自定义sessionFactory
        sessionManager.setSessionFactory(sessionFactory());
        return sessionManager;
    }

    @Bean
    public RedisShiroSessionDAO redisShiroSessionDAO(){
        RedisShiroSessionDAO redisShiroSessionDAO = new RedisShiroSessionDAO();
        return redisShiroSessionDAO;
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(MyUserRealm userRealm, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置自定义realm.
        securityManager.setRealm(userRealm);
//        //配置记住我 参考博客：
//        securityManager.setRememberMeManager(rememberMeManager());
        //配置 ehcache缓存管理器 参考博客：
        securityManager.setCacheManager(getEhCacheManager());

        //配置自定义session管理，使用ehcache 或redis
        securityManager.setSessionManager(sessionManager);

        return securityManager;
    }

    /**
     * 退出过滤器
     */
    public LogoutFilter logoutFilter()
    {
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setLoginUrl(loginUrl);
        return logoutFilter;
    }
    /**
     * 自定义在线用户处理过滤器
     */
    public OnlineSessionFilter onlineSessionFilter()
    {
        OnlineSessionFilter onlineSessionFilter = new OnlineSessionFilter();
        onlineSessionFilter.setLoginUrl(loginUrl);
        return onlineSessionFilter;
    }
//    /**
//     * 自定义在线用户同步过滤器
//     */
//    public SyncOnlineSessionFilter syncOnlineSessionFilter()
//    {
//        SyncOnlineSessionFilter syncOnlineSessionFilter = new SyncOnlineSessionFilter();
//        return syncOnlineSessionFilter;
//    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();


        Map<String, Filter> myfilters = new LinkedHashMap<>();

        myfilters.put("logout", logoutFilter());
        myfilters.put("onlineSession", onlineSessionFilter());
//        myfilters.put("syncOnlineSession", syncOnlineSessionFilter());

        shiroFilterFactoryBean.setFilters(myfilters);

        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        shiroFilterFactoryBean.setUnauthorizedUrl("/");



        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/swagger-resources/**", "anon");

        filterMap.put("/statics/**", "anon");
        filterMap.put("/login.html", "anon");
        filterMap.put("/sys/login", "anon");
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/captcha.jpg", "anon");

        // 退出 logout地址，shiro去清除session
        filterMap.put("/logout", "logout");

        filterMap.put("/**", "authc");

        filterMap.put("/**", "onlineSession");
//        filterMap.put("/**", "syncOnlineSession");




//        filterMap.put("/**", "onlineSession,syncOnlineSession");




        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);



        return shiroFilterFactoryBean;
    }


    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 配置session监听
     * @return
     */
    @Bean("sessionListener")
    public ShiroSessionListener sessionListener(){
        ShiroSessionListener sessionListener = new ShiroSessionListener();
        return sessionListener;
    }

    /**
     * 配置会话ID生成器
     * @return
     */
    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    /**
     * SessionDAO的作用是为Session提供CRUD并进行持久化的一个shiro组件
     * MemorySessionDAO 直接在内存中进行会话维护
     * EnterpriseCacheSessionDAO  提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
     * @return
     */
//    @Bean
//    public SessionDAO sessionDAO() {
//        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
//        //使用ehCacheManager
////        enterpriseCacheSessionDAO.setCacheManager(ehCacheManager());
//        //设置session缓存的名字 默认为 shiro-activeSessionCache
//        enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
//        //sessionId生成器
//        enterpriseCacheSessionDAO.setSessionIdGenerator(sessionIdGenerator());
//
//        return enterpriseCacheSessionDAO;
//    }



    /**
     * 配置保存sessionId的cookie
     * 注意：这里的cookie 不是上面的记住我 cookie 记住我需要一个cookie session管理 也需要自己的cookie
     * @return
     */
    @Bean("sessionIdCookie")
    public SimpleCookie sessionIdCookie(){
        //这个参数是cookie的名称
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：

        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //maxAge=-1表示浏览器关闭时失效此Cookie
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }
}
