package com.chenbitao.noodle_shop.domain.model;

import lombok.Builder;
import lombok.Data;

/**
 * 商品基础类
 */
@Data
@Builder
public class Goods implements IOrderItem {
    private String id;
    private String name;
    private float price;
}