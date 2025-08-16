package com.chenbitao.noodle_shop.vo;

import java.util.List;

import com.chenbitao.noodle_shop.domain.model.DiscountRule;
import com.chenbitao.noodle_shop.domain.model.Money;

public class OrderResult {
    private boolean holiday;
    private Money amount;
    private List<DiscountRule> rules;

    public OrderResult(boolean holiday, Money amount, List<DiscountRule> rules) {
        this.holiday = holiday;
        this.amount = amount;
        this.rules = rules;
    }

    public boolean isHoliday() {
        return holiday;
    }

    public List<DiscountRule> getRules() {
        return rules;
    }

    public Money getAmount() {
        return amount;
    }
}