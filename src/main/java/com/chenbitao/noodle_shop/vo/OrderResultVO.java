package com.chenbitao.noodle_shop.vo;

import java.util.List;

import com.chenbitao.noodle_shop.domain.model.DiscountRule;
import com.chenbitao.noodle_shop.domain.model.Money;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResultVO {
    private boolean holiday;
    private Money originalCost;
    private Money cost;
    private List<DiscountRule> rules;
    private List<DiscountRule> appliedRules;
}