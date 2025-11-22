package com.chenbitao.noodle_shop.vo;

import com.chenbitao.noodle_shop.enums.GoodsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestVO {
    /**
     * 商品ID
     */
    private Long id;
    /**
     * 商品名称
     */
    private String goodName;
    /**
     * 数量
     */
    private int count;
    /**
     * 商品类型
     */
    private GoodsType type;
}