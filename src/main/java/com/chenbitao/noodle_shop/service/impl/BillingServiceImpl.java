package com.chenbitao.noodle_shop.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.chenbitao.noodle_shop.domain.model.*;
import com.chenbitao.noodle_shop.service.BillingService;

public class BillingServiceImpl implements BillingService {

    @Override
    public Money calculateTotal(Order order) {
        BigDecimal total = BigDecimal.ZERO;
        for (MenuItem item : order.getItems())
            total = total.add(item.getPrice());
        return new Money(total);
    }

    @Override
    public Money calculateWithDiscount(Order order, List<DiscountRule> rules, List<MenuItem> excluded) {
        // 计算订单原价，包括所有商品
        BigDecimal total = calculateTotal(order).getAmount();

        // 优先处理异常情况
        if (rules == null || rules.isEmpty()) {
            return new Money(total);
        }
        // 统计可参与折扣的金额，排除 excluded 中的商品
        BigDecimal discountBase = BigDecimal.ZERO;
        for (MenuItem item : order.getItems()) {
            if (excluded == null || !excluded.contains(item)) {
                discountBase = discountBase.add(item.getPrice());
            }
        }

        // 计算折扣，取满足条件的最大折扣
        BigDecimal discount = BigDecimal.ZERO;
        if (rules != null) {
            for (DiscountRule rule : rules) {
                if (discountBase.compareTo(rule.getThreshold()) >= 0) {
                    discount = discount.max(rule.getDiscount());
                }
            }
        }

        return new Money(total.subtract(discount));
    }

    @Override
    public Money calculateWithoutDiscount(Order order) {
        // 原价计算，不应用任何折扣
        return calculateTotal(order);
    }

}
