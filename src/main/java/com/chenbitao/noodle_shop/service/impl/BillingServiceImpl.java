package com.chenbitao.noodle_shop.service.impl;

import java.util.List;

import com.chenbitao.noodle_shop.domain.model.*;
import com.chenbitao.noodle_shop.service.BillingService;

public class BillingServiceImpl implements BillingService {

    @Override
    public Money calculateTotal(Order order) {
        int total = 0;
        for (MenuItem item : order.getItems()) total += item.getPrice();
        return new Money(total);
    }

    @Override
    public Money calculateWithDiscount(Order order, List<DiscountRule> rules, List<MenuItem> excluded) {
        int total = calculateTotal(order).getAmount();
        int discountBase = 0;
        for (MenuItem item : order.getItems())
            if (!excluded.contains(item)) discountBase += item.getPrice();

        int discount = 0;
        for (DiscountRule rule : rules)
            if (discountBase >= rule.getThreshold()) discount = Math.max(discount, rule.getDiscount());

        return new Money(total - discount);
    }

}
