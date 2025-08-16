package com.chenbitao.noodle_shop.application;

import java.util.List;

import com.chenbitao.noodle_shop.domain.model.DiscountRule;
import com.chenbitao.noodle_shop.domain.model.MenuItem;
import com.chenbitao.noodle_shop.domain.model.Money;
import com.chenbitao.noodle_shop.domain.model.Order;
import com.chenbitao.noodle_shop.service.BillingService;
import com.chenbitao.noodle_shop.service.impl.BillingServiceImpl;

public class OrderService {
    private final BillingService billingService = new BillingServiceImpl();

    public Money calculateWithDiscount(Order order, List<DiscountRule> rules, List<MenuItem> excluded) {
        return billingService.calculateWithDiscount(order, rules, excluded);
    }

    public Money calculateWithoutDiscount(Order order) {
        return billingService.calculateWithoutDiscount(order);
    }
}