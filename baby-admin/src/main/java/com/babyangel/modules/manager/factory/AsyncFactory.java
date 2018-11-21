package com.babyangel.modules.manager.factory;

import com.babyangel.common.constant.BabyConstants;
import com.babyangel.common.utils.AddressUtils;
import com.babyangel.common.utils.LogUtils;
import com.babyangel.common.utils.ServletUtils;
import com.babyangel.common.utils.security.ShiroUtils;
import com.babyangel.common.utils.SpringContextUtils;
import com.babyangel.modules.monitor.entity.OnlineSession;
import com.babyangel.modules.monitor.entity.SysLogininforEntity;
import com.babyangel.modules.monitor.entity.SysOperLogEntity;
import com.babyangel.modules.monitor.entity.SysUserOnlineEntity;

import com.babyangel.modules.monitor.service.SysLogininforService;
import com.babyangel.modules.monitor.service.SysOperLogService;
import com.babyangel.modules.monitor.service.SysUserOnlineService;
import com.babyangel.modules.sys.entity.SysUserEntity;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 * 
 * @author liuhulu
 *
 */
public class AsyncFactory
{
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    /**
     * 同步session到数据库
     *
     * @param session 在线用户会话
     * @return 任务task
     */
//    public static TimerTask syncSessionToDb(final Session session)
//    {
//        return new TimerTask()
//        {
//            @Override
//            public void run()
//            {
//                SysUserOnlineEntity online = new SysUserOnlineEntity();
//                online.setSessionid((String) session.getId());
//                SysUserEntity user = (SysUserEntity)session.getAttribute("onlineUser");
//                online.setDeptName(user.getDeptName());
//                online.setLoginName(user.getUsername());
//                online.setStartTimestamp(session.getStartTimestamp());
//                online.setLastAccessTime(session.getLastAccessTime());
//                online.setExpireTime(session.getTimeout());
//                online.setIpaddr(session.getHost());
//                online.setLoginLocation(AddressUtils.getRealAddressByIP(session.getHost()));
//                online.setBrowser(session.getAttribute("browser").toString());
//                online.setOs(session.getAttribute("os").toString());
//                //online.setStatus(OnlineSession.OnlineStatus.on_line);
//                //online.setSession(session);
//                SpringContextUtils.getBean(SysUserOnlineService.class).insert(online);
//            }
//        };
//    }

    /**
     * 同步session到数据库
     *
     * @param session 在线用户会话
     * @return 任务task
     */
    public static TimerTask syncSessionToDb(final OnlineSession session)
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                SysUserOnlineEntity online = new SysUserOnlineEntity();
                online.setSessionid(String.valueOf(session.getId()));
                online.setDeptName(session.getDeptName());
                online.setLoginName(session.getLoginName());
                online.setStartTimestamp(session.getStartTimestamp());
                online.setLastAccessTime(session.getLastAccessTime());
                online.setExpireTime(session.getTimeout());
                online.setIpaddr(session.getHost());
                online.setLoginLocation(AddressUtils.getRealAddressByIP(session.getHost()));
                online.setBrowser(session.getBrowser());
                online.setOs(session.getOs());
                online.setStatus(session.getStatus());
                online.setSession(session);
                SysUserOnlineService sysUserOnlineService = SpringContextUtils.getBean(SysUserOnlineService.class);
                SysUserOnlineEntity user = sysUserOnlineService.selectOnlineBySessionId(online.getSessionid());
                if(user==null){
                    sysUserOnlineService.insert(online);
                }else{
                    online.setId(user.getId());
                    sysUserOnlineService.updateAllColumnById(online);
                }


            }
        };
    }

    /**
     * 操作日志记录
     * 
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final SysOperLogEntity operLog)
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                // 远程查询操作地点
                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringContextUtils.getBean(SysOperLogService.class).insert(operLog);
            }
        };
    }

    /**
     * 记录登陆信息
     * 
     * @param username 用户名
     * @param status 状态
     * @param message 消息
     * @param args 列表
     * @return 任务task
     */
    public static TimerTask recordLogininfor(final String username, final String status, final String message, final Object... args)
    {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = ShiroUtils.getIp();
        return new TimerTask()
        {
            @Override
            public void run()
            {
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ip));
                s.append(AddressUtils.getRealAddressByIP(ip));
                s.append(LogUtils.getBlock(username));
                s.append(LogUtils.getBlock(status));
                s.append(LogUtils.getBlock(message));
                // 打印信息到日志
                sys_user_logger.info(s.toString(), args);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
               // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();

                // 封装对象
                SysLogininforEntity logininfor = new SysLogininforEntity();
                logininfor.setLoginName(username);
                logininfor.setIpaddr(ip);
                logininfor.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
                logininfor.setBrowser(browser);
                logininfor.setOs(os);
                logininfor.setMsg(message);
                // 日志状态
                if (BabyConstants.LOGIN_SUCCESS.equals(status) || BabyConstants.LOGOUT.equals(status))
                {
                    logininfor.setStatus(BabyConstants.SUCCESS);
                }
                else if (BabyConstants.LOGIN_FAIL.equals(status))
                {
                    logininfor.setStatus(BabyConstants.FAIL);
                }
                logininfor.setLoginTime(new Date());
                // 插入数据
                SpringContextUtils.getBean(SysLogininforService.class).insert(logininfor);
            }
        };
    }
}
