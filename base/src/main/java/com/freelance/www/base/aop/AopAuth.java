package com.freelance.www.base.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.freelance.www.base.annotation.RestAuth;
import com.freelance.www.base.annotation.RestFlag;
import com.freelance.www.base.vo.RestResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Aspect
public class AopAuth {

    public AopAuth() {
    }

    @Pointcut("@annotation(com.freelance.www.base.annotation.RestAuth)")
    public void pointCut() {
    }

    @Around("pointCut() && @annotation(restAuth) && args(req, resp, ..)")
    public void handleControllerMethod(ProceedingJoinPoint pjp, RestAuth restAuth, HttpServletRequest req, HttpServletResponse resp) throws Throwable {
        long now = System.currentTimeMillis();
        log.info("RestIIP {} {} {}", new Object[]{req.getMethod(), req.getRequestURL(), req.getQueryString()});
        resp.setContentType("plain/text");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setStatus(200);

        try {
            if (!this.CheckToken(req, resp, restAuth)) {
                return;
            }

            Object object = pjp.proceed();
            if (object != null) {
                if (object instanceof String) {
                    resp.getWriter().print(object);
                } else {
                    resp.setContentType("application/json");
                    resp.getWriter().print(JSON.toJSONString(object, new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNonStringKeyAsString}));
                }
            }
        } catch (Throwable var11) {
            log.error("AopIIP", var11);
            resp.setContentType("application/json");
            resp.getWriter().print(JSON.toJSONString(RestResultVO.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), var11.getMessage())));
        } finally {
            log.info("RestIIP {} {} use time: {}ms", new Object[]{req.getMethod(), req.getRequestURL(), System.currentTimeMillis() - now});
        }

    }

    private boolean CheckToken(HttpServletRequest req, HttpServletResponse resp, RestAuth restAuth) {
        if (RestFlag.contains(restAuth.value(), RestFlag.NO_TOKEN)) {
            return true;
        } else {
            String header = req.getHeader("iip-token");
            if (StringUtils.isBlank(header)) {
                resp.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            } else {
                return true;
            }
        }
    }

    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
