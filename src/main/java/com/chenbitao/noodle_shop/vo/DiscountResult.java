package com.chenbitao.noodle_shop.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import com.chenbitao.noodle_shop.domain.DiscountRule;
import com.chenbitao.noodle_shop.domain.Money;

@Data
@AllArgsConstructor
public class DiscountResult {
    /**
     * 最终价格
     */
    private Money finalPrice;
    /**
     * 符合条件的规则
     */
    private List<DiscountRule> applied;
}