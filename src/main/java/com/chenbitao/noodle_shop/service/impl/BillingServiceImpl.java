package com.chenbitao.noodle_shop.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.chenbitao.noodle_shop.domain.Combine;
import com.chenbitao.noodle_shop.domain.DiscountRule;
import com.chenbitao.noodle_shop.domain.IOrderItem;
import com.chenbitao.noodle_shop.domain.Money;
import com.chenbitao.noodle_shop.domain.Order;
import com.chenbitao.noodle_shop.service.IBillingService;
import com.chenbitao.noodle_shop.vo.DiscountResult;

@Service
public class BillingServiceImpl implements IBillingService {

    @Override
    public Money calculateTotal(Order order) {
        BigDecimal total = BigDecimal.ZERO;
        for (IOrderItem item : order.getItems().keySet()) {
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
    public DiscountResult calculateWithDiscount(Order order, List<DiscountRule> rules, List<String> excludedCodes) {
        // 计算订单原价，包括所有商品
        BigDecimal total = calculateTotal(order).getAmount();

        // 优先处理没有打折规则的情况
        if (rules == null || rules.isEmpty()) {
            return new DiscountResult(new Money(total), List.of());
        }
        // 统计可参与折扣的金额，排除 excluded 中的商品
        BigDecimal discountBase = BigDecimal.ZERO;
        for (IOrderItem item : order.getItems().keySet()) {
            if (excludedCodes == null || !excludedCodes.contains(item.getCode())) {
                BigDecimal price = item.getPrice();
                int count = order.getItemCount(item);
                discountBase = discountBase.add(price.multiply(BigDecimal.valueOf(count)));
            }
        }

        // 计算折扣，取满足条件的最大折扣
        BigDecimal discount = BigDecimal.ZERO;
        DiscountRule appliedRule = null;

        for (DiscountRule rule : rules) {
            if (discountBase.compareTo(rule.getThreshold()) >= 0 && rule.getDiscount().compareTo(discount) > 0) {
                discount = rule.getDiscount();
                appliedRule = rule;
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

    public Map<Combine, Integer> matchSetMeals(Order order, List<Combine> combines) {
        Map<Combine, Integer> result = new HashMap<>();

        for (Combine combine : combines) {
            int count = 0;
            while (canApplyCombine(order, combine)) {
                applyCombine(order, combine);
                addCombine(order, combine);
                count++;
            }
            if (count > 0) {
                result.put(combine, count);
            }
        }

        return result;
    }

    /** 判断订单里是否还可以再用一个套餐 */
    public boolean canApplyCombine(Order order, Combine combine) {
        // 先把订单里的 itemId -> count 映射出来，方便快速查找
        Map<String, Integer> orderCountMap = order.getItems().entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().getCode(), Map.Entry::getValue));

        // 检查套餐里的每个商品是否都存在，且数量 >= 1
        for (String goodsId : combine.getItems()) {
            Integer count = orderCountMap.get(goodsId);
            if (count == null || count < 1) {
                return false;
            }
        }
        return true;
    }

    /** 从订单中扣掉一个套餐对应的商品数量 */
    public void applyCombine(Order order, Combine combine) {
        // 构造 itemId -> I OrderItem 的映射，方便快速查找
        Map<String, IOrderItem> orderItemMap = order.getItems().keySet().stream()
                .collect(Collectors.toMap(IOrderItem::getCode, item -> item));

        // 遍历套餐里的商品，逐个在订单中扣减
        for (String goodsId : combine.getItems()) {
            IOrderItem item = orderItemMap.get(goodsId);
            if (item != null) {
                order.removeItem(item, 1); // 每个套餐项默认扣掉数量1
            }
        }
    }

    /**
     * 从订单中增加一个套餐对应的套餐数量
     */
    public void addCombine(Order order, Combine combine) {
        for (IOrderItem item : order.getItems().keySet()) {
            if (item.getCode().equals(combine.getCode())) {
                order.addItem(item, 1); // 每个套餐项默认增加数量1
                break;
            }
        }
    }
}
