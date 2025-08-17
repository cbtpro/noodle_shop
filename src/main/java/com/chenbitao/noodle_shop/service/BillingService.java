package com.chenbitao.noodle_shop.service;

import java.util.List;
import java.util.Map;

import com.chenbitao.noodle_shop.domain.model.Combine;
import com.chenbitao.noodle_shop.domain.model.DiscountRule;
import com.chenbitao.noodle_shop.domain.model.Money;
import com.chenbitao.noodle_shop.domain.model.Order;
import com.chenbitao.noodle_shop.vo.DiscountResult;

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
    public DiscountResult calculateWithDiscount(Order order, List<DiscountRule> rules, List<String> excluded);

    /**
     * 计算订单的原价，不应用任何折扣
     * @param order 订单
     * @return  订单原价
     */
    public Money calculateWithoutDiscount(Order order);

    /**
     * 贪心算法自动匹配套餐
     * @param order
     * @param combines
     * @return order 匹配的套餐数量
     */
    public Map<Combine, Integer> matchSetMeals(Order order, List<Combine> combines);

    /** 判断订单里是否还可以再用一个套餐 */
    public boolean canApplyCombine(Order order, Combine combine);

    /** 从订单中扣掉一个套餐对应的商品数量 */
    public void applyCombine(Order order, Combine combine);
}
