package com.chenbitao.noodle_shop.vo;

import com.chenbitao.noodle_shop.domain.model.DiscountRule;
import com.chenbitao.noodle_shop.domain.model.Money;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DiscountResult {
    private Money finalPrice;             // 最终价格
    private List<DiscountRule> applied;   // 符合条件的规则
}