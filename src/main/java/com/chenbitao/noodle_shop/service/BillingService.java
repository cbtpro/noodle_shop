package com.chenbitao.noodle_shop.service;

import java.util.List;

import com.chenbitao.noodle_shop.domain.model.DiscountRule;
import com.chenbitao.noodle_shop.domain.model.MenuItem;
import com.chenbitao.noodle_shop.domain.model.Money;
import com.chenbitao.noodle_shop.domain.model.Order;

public interface BillingService {

    /**
     * 计算订单的总价
     * @param order 订单
     * @return  订单总价
     */
    public Money calculateTotal(Order order);

    /**
     * 计算订单打折后的总价
     * @param order 订单
     * @return  打折后订单总价
     */
    public Money calculateWithDiscount(Order order, List<DiscountRule> rules, List<MenuItem> excluded);

    /**
     * 计算订单的原价，不应用任何折扣
     * @param order 订单
     * @return  订单原价
     */
    public Money calculateWithoutDiscount(Order order);
}
