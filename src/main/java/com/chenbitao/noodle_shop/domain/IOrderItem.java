package com.chenbitao.noodle_shop.domain;

import java.math.BigDecimal;

public interface IOrderItem {
    Long getId();
    String getCode();
    String getName();
    BigDecimal getPrice();
}