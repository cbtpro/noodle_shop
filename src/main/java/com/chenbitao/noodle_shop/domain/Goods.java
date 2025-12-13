package com.chenbitao.noodle_shop.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chenbitao.noodle_shop.domain.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 商品基础类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_base_goods")
public class Goods extends BaseEntity<Long> implements IOrderItem {
    private String code;
    private String name;
    private BigDecimal price;
}