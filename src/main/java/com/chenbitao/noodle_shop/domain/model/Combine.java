package com.chenbitao.noodle_shop.domain.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * 套餐
 */
@Data
@Builder
public class Combine implements OrderItem {
    private String id;
    private String name;
    private float price;
    private List<String> items; // 商品id列表
    private List<Goods> goodsList;
}