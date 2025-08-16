package com.chenbitao.noodle_shop.config;

import com.chenbitao.noodle_shop.exception.OrderCalculationException;
import com.chenbitao.noodle_shop.vo.ApiResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Value("${spring.profiles.active:prod}")
    private String activeProfile;

    @ExceptionHandler(OrderCalculationException.class)
    public ResponseEntity<ApiResponseBody<Object>> handleOrderException(OrderCalculationException ex) {
        String trace = null;
        if ("dev".equalsIgnoreCase(activeProfile)) {
            trace = Arrays.toString(ex.getStackTrace());
        }

        ApiResponseBody<Object> response = ApiResponseBody.fail(
                400,
                "Order Calculation Failed",
                ex.getMessage(),
                trace);

        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseBody<Object>> handleException(Exception ex) {
        String trace = null;
        // 如果是dev,就可以返回堆栈信息,同时所有换记录日志文件
        if ("dev".equalsIgnoreCase(activeProfile)) {
            trace = Arrays.toString(ex.getStackTrace());
        }

        ApiResponseBody<Object> response = ApiResponseBody.fail(
                500,
                "Internal Server Error",
                ex.getMessage(),
                trace);

        return ResponseEntity.status(500).body(response);
    }
}
