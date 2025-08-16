package com.chenbitao.noodle_shop.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestVO {
    /**
     * 商品
     */
    private String good;
    /**
     * 数量
     */
    private int count;
}
