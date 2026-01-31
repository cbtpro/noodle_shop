package com.chenbitao.noodle_shop.annotation.aspect;

import com.chenbitao.noodle_shop.annotation.LogExecutionTime;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class LogExecutionTimeAspect {

    private final ObjectMapper objectMapper;

    public LogExecutionTimeAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Around("@annotation(com.chenbitao.noodle_shop.annotation.LogExecutionTime)")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Method targetMethod = joinPoint
                .getTarget()
                .getClass()
                .getMethod(method.getName(), method.getParameterTypes());

        LogExecutionTime annotation = targetMethod.getAnnotation(LogExecutionTime.class);

        String prefix = annotation != null ? annotation.prefix() : "";

        long start = System.currentTimeMillis();

        Object result = null;
        Throwable throwable = null;

        try {
            result = joinPoint.proceed();
            return result;
        } catch (Throwable t) {
            throwable = t;
            throw t;
        } finally {
            long cost = System.currentTimeMillis() - start;

            StringBuilder logBuilder = new StringBuilder();

            // prefix 放最前，方便日志检索
            if (!prefix.isEmpty()) {
                logBuilder.append("[").append(prefix).append("] ");
            }

            logBuilder.append("method=")
                    .append(targetMethod.getDeclaringClass().getSimpleName())
                    .append(".")
                    .append(targetMethod.getName())
                    .append(" | cost=")
                    .append(cost)
                    .append("ms");

            if (annotation != null && annotation.logArgs()) {
                logBuilder.append(" | args=");
                logBuilder.append(toJson(joinPoint.getArgs()));
            }

            if (annotation != null && annotation.logResult() && throwable == null) {
                logBuilder.append(" | result=");
                logBuilder.append(toJson(result));
            }

            if (throwable != null) {
                logBuilder.append(" | exception=")
                        .append(throwable.getClass().getSimpleName())
                        .append(": ")
                        .append(throwable.getMessage());
            }

            log.info(logBuilder.toString());
        }
    }

    private String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "[json serialize error]";
        }
    }
}