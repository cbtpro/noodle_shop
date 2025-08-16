package com.chenbitao.noodle_shop.service;

import java.util.List;

import com.chenbitao.noodle_shop.domain.model.DiscountRule;
import com.chenbitao.noodle_shop.domain.model.MenuItem;
import com.chenbitao.noodle_shop.domain.model.Money;
import com.chenbitao.noodle_shop.domain.model.Order;

public interface BillingService {


    public Money calculateTotal(Order order);

    public Money calculateWithDiscount(Order order, List<DiscountRule> rules, List<MenuItem> excluded);
}
