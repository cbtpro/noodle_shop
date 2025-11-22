package com.chenbitao.noodle_shop.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品基础类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("t_base_goods")
public class Goods extends BaseEntity<Long> implements IOrderItem {
    private String code;
    private String name;
    private BigDecimal price;
}