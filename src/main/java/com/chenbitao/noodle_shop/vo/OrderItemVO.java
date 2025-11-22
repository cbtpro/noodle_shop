package com.chenbitao.noodle_shop.vo;

import com.chenbitao.noodle_shop.domain.BaseEntity;
import com.chenbitao.noodle_shop.domain.Money;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class OrderItemVO extends BaseEntity<Long> {
    private Long id;
    private String name;
    private Money price;
    private int count;
}
