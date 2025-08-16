package com.chenbitao.noodle_shop.vo;

import java.util.List;

import com.chenbitao.noodle_shop.domain.model.DiscountRule;
import com.chenbitao.noodle_shop.domain.model.Money;

public class OrderResult {
    private boolean holiday;
    private Money originalCost;
    private Money cost;
    private List<DiscountRule> rules;

    public OrderResult(boolean holiday, Money originalCost, Money cost, List<DiscountRule> rules) {
        this.holiday = holiday;
        this.originalCost = originalCost;
        this.cost = cost;
        this.rules = rules;
    }

    public boolean isHoliday() {
        return holiday;
    }

    public Money getOriginalCost() {
        return originalCost;
    }

    public List<DiscountRule> getRules() {
        return rules;
    }

    public Money getCost() {
        return cost;
    }
}