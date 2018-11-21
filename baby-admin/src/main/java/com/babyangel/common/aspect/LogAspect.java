package com.babyangel.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.babyangel.common.annotation.Log;
import com.babyangel.common.enums.BusinessStatus;
import com.babyangel.common.utils.ServletUtils;
import com.babyangel.common.utils.security.ShiroUtils;
import com.babyangel.common.utils.StringUtils;
import com.babyangel.modules.monitor.entity.SysOperLogEntity;
import com.babyangel.modules.sys.entity.SysUserEntity;
import com.babyangel.modules.manager.AsyncManager;
import com.babyangel.modules.manager.factory.AsyncFactory;
import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * 操作日志记录处理
 * 
 * @author ruoyi
 */
@Aspect
@Component
public class LogAspect
{
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    // 配置织入点
    @Pointcut("@annotation(com.babyangel.common.annotation.Log)")
    public void logPointCut()
    {
    }

    /**
     * 前置通知 用于拦截操作
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doBefore(JoinPoint joinPoint)
    {
        handleLog(joinPoint, null);
    }

    /**
     * 拦截异常操作
     * 
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e)
    {
        handleLog(joinPoint, e);
    }

    /**
     * 记录日志
     * @param joinPoint
     * @param e
     */
    protected void handleLog(final JoinPoint joinPoint, final Exception e)
    {
        try
        {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null)
            {
                return;
            }

            // 获取当前的用户
            SysUserEntity currentUser = ShiroUtils.getUserEntity();

            // *========数据库日志=========*//
            SysOperLogEntity operLog = new SysOperLogEntity();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
            String ip = ShiroUtils.getIp();
            operLog.setOperIp(ip);

            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            if (currentUser != null)
            {
                operLog.setOperName(currentUser.getUsername());
                if (StringUtils.isNotEmpty(currentUser.getDeptName()))
                {
                    operLog.setDeptName(currentUser.getDeptName());
                }
            }

            if (e != null)
            {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 处理设置注解上的参数
            getControllerMethodDescription(controllerLog, operLog);
            //保存请求的参数
            if(controllerLog.saveRequestData()) {
                Object[] args = joinPoint.getArgs();
                String params = new Gson().toJson(args[0]);
                operLog.setOperParam(params);
            }
            operLog.setOperTime(new Date());
            // 保存数据库
            AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
        }
        catch (Exception exp)
        {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     * 
     * @param log rizhi
     * @param operLog
     * @return 方法描述
     * @throws Exception
     */
    public void getControllerMethodDescription(Log log, SysOperLogEntity operLog) throws Exception
    {
        // 设置action动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
//        if (log.saveRequestData())
//        {
//            // 获取参数的信息，传入到数据库中。
//            setRequestValue(operLog);
//        }
    }

    /**
     * 获取请求的参数，放到log中
     * 
     * @param operLog
     */
    private void setRequestValue(SysOperLogEntity operLog)
    {
        Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();
        String params = JSONObject.toJSONString(map);
        operLog.setOperParam(StringUtils.substring(params, 0, 255));
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(Log.class);
        }
        return null;
    }
}