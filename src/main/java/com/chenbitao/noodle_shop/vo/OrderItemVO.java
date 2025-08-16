package com.chenbitao.noodle_shop.vo;

import com.chenbitao.noodle_shop.domain.model.Money;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemVO {
    private String id;
    private String name;
    private Money price;
    private int count;
}
