package com.chenbitao.noodle_shop.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chenbitao.noodle_shop.domain.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_base_non_discount_goods")
public class NonDiscountGoods extends BaseEntity<Long> {
    private String code;
}
