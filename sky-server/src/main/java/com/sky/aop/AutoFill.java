package com.sky.aop;

import com.sky.annotation.AutoFillAdd;
import com.sky.annotation.AutoFillUpdate;
import com.sky.context.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 自动注解
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/5 09:36:52
 */
@Component
@Slf4j
@Aspect
public class AutoFill {

    @Pointcut("@annotation(com.sky.annotation.AutoFillUpdate) || @annotation(com.sky.annotation.AutoFillAdd)")
    public void pt() {
    }

    @Before("pt()")
    public void autoFill(JoinPoint jp) {
        log.info("拦截到了");
        MethodSignature ms = (MethodSignature) jp.getSignature();
        Method method = ms.getMethod();
        //获取方法上的注解
        AutoFillAdd autoFillAdd = method.getAnnotation(AutoFillAdd.class);
        //AutoFillUpdate autoFillUpdate = method.getAnnotation(AutoFillUpdate.class);
        //区分为更新
        try {
            Object[] args = jp.getArgs();
            Object param = args[0];
            Class<?> aClass = param.getClass();
            //获得方法
            Method setUpdateTime = aClass.getMethod("setUpdateTime", LocalDateTime.class);
            Method setUpdateUser = aClass.getMethod("setUpdateUser", Long.class);
            //调用setValue方法
            setUpdateTime.invoke(param, LocalDateTime.now());
            setUpdateUser.invoke(param, BaseContext.getCurrentId());
            //区分未新新增
            if (Objects.nonNull(autoFillAdd)) {
                Method setCreateTime = aClass.getMethod("setCreateTime", LocalDateTime.class);
                Method setCreateUser = aClass.getMethod("setCreateUser", Long.class);
                //调用setValue方法
                setCreateTime.invoke(param, LocalDateTime.now());
                setCreateUser.invoke(param, BaseContext.getCurrentId());
            }
        } catch (Exception e) {
            log.info("-----自动注入失败-----", e);
        }

    }
}
