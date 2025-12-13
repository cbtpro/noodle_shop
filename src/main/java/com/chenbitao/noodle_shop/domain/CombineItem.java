package com.chenbitao.noodle_shop.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chenbitao.noodle_shop.domain.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 套餐包含的商品明细
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_base_combine_item")
public class CombineItem extends BaseEntity<Long> {

    private Long combineId;    // 套餐 ID
    private String goodsCode;  // 商品 code
}
