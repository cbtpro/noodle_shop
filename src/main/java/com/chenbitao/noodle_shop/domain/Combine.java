package com.chenbitao.noodle_shop.domain;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chenbitao.noodle_shop.domain.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 套餐
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_base_combine")
public class Combine extends BaseEntity<Long> implements IOrderItem {
    private String code;
    private String name;
    private BigDecimal price;

    /** 套餐明细（数据库不直接存，只用 @TableField(exist=false) 注解） */
    @TableField(exist = false)
    private List<CombineItem> itemList;

    /** 套餐对应商品实体列表（业务层填充） */
    @TableField(exist = false)
    private List<Goods> goodsList;
}