package com.bob.identification.component;

import com.bob.identification.common.util.IPUtil;
import com.bob.identification.service.IdentificationVisitService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 统计访问处理切面
 * Created by LittleBob on 2021/1/4/004.
 */
@Aspect
@Component
@Order(2)
public class VisitAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(VisitAspect.class);

    @Autowired
    private IdentificationVisitService visitService;

    @Pointcut("execution(public * com.bob.identification.controller.HomeController.*(..))")
    public void visitAspect() {
    }

    @Before("visitAspect()")
    public void doBefore() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ipAddress = IPUtil.getIpAddress(request);
        LOGGER.info("访问用户IP地址：" + ipAddress);
        visitService.operateIP(ipAddress);
    }
}
