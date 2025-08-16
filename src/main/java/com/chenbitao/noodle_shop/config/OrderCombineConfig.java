package com.chenbitao.noodle_shop.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.config.order.combine")
public class OrderCombineConfig {

    /**
     * 是否自动合并订单
     */
    private boolean auto = false;
}