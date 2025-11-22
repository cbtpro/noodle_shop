package com.chenbitao.noodle_shop.domain;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 套餐
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("t_base_combine")
public class Combine extends BaseEntity<Long> implements IOrderItem {
    private String code;
    private String name;
    private BigDecimal price;
    private List<String> items; // 商品Code列表
    private List<Goods> goodsList;
}