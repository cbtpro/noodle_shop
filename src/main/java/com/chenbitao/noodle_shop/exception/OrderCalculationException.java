package com.chenbitao.noodle_shop.exception;

/**
 * 订单计算异常
 */
public class OrderCalculationException extends RuntimeException {

    public OrderCalculationException(String message) {
        super(message);
    }

    public OrderCalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}