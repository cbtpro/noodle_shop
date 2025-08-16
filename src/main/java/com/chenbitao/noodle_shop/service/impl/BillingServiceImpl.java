package com.chenbitao.noodle_shop.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chenbitao.noodle_shop.domain.model.*;
import com.chenbitao.noodle_shop.service.BillingService;
import com.chenbitao.noodle_shop.vo.DiscountResult;

public class BillingServiceImpl implements BillingService {

    @Override
    public Money calculateTotal(Order order) {
        BigDecimal total = BigDecimal.ZERO;
        for (MenuItem item : order.getItems().keySet()) {
            // 获取商品数量
            int count = order.getItemCount(item);
            // 商品价格
            BigDecimal price = item.getPrice();
            // 累加每个商品的总价
            total = total.add(price.multiply(BigDecimal.valueOf(count)));
        }

        return new Money(total);
    }

    @Override
    public DiscountResult calculateWithDiscount(Order order, List<DiscountRule> rules, List<MenuItem> excluded) {
        // 计算订单原价，包括所有商品
        BigDecimal total = calculateTotal(order).getAmount();

        // 优先处理没有打折规则的情况
        if (rules == null || rules.isEmpty()) {
            return new DiscountResult(new Money(total), List.of());
        }
        // 统计可参与折扣的金额，排除 excluded 中的商品
        BigDecimal discountBase = BigDecimal.ZERO;
        for (MenuItem item : order.getItems().keySet()) {
            if (excluded == null || !excluded.contains(item)) {
                BigDecimal price = item.getPrice();
                int count = order.getItemCount(item);
                discountBase = discountBase.add(price.multiply(BigDecimal.valueOf(count)));
            }
        }

        // 计算折扣，取满足条件的最大折扣
        BigDecimal discount = BigDecimal.ZERO;
        DiscountRule appliedRule = null;

        for (DiscountRule rule : rules) {
            if (discountBase.compareTo(rule.getThreshold()) >= 0) {
                if (rule.getDiscount().compareTo(discount) > 0) {
                    discount = rule.getDiscount();
                    appliedRule = rule;
                }
            }
        }

        List<DiscountRule> applied = appliedRule == null ? List.of() : List.of(appliedRule);
        return new DiscountResult(new Money(total.subtract(discount)), applied);
    }

    @Override
    public Money calculateWithoutDiscount(Order order) {
        // 原价计算，不应用任何折扣
        return calculateTotal(order);
    }

    @Override
    public Map<SetMeal, Integer> matchSetMeals(Order order, List<SetMeal> setMeals) {
        Map<SetMeal, Integer> result = new HashMap<>();

        for (SetMeal setMeal : setMeals) {
            int count = 0;
            while (canApplySetMeal(order, setMeal)) {
                applySetMeal(order, setMeal);
                count++;
            }
            if (count > 0)
                result.put(setMeal, count);
        }

        return result;
    }

    /** 判断订单里是否还可以再用一个套餐 */
    public boolean canApplySetMeal(Order order, SetMeal setMeal) {
        for (Map.Entry<MenuItem, Integer> entry : setMeal.getItems().entrySet()) {
            if (order.getItemCount(entry.getKey()) < entry.getValue())
                return false;
        }
        return true;
    }

    /** 从订单中扣掉一个套餐对应的商品数量 */
    public void applySetMeal(Order order, SetMeal setMeal) {
        for (Map.Entry<MenuItem, Integer> entry : setMeal.getItems().entrySet()) {
            order.removeItem(entry.getKey(), entry.getValue());
        }
    }
}
