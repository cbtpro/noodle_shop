package com.chenbitao.noodle_shop.application;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chenbitao.noodle_shop.domain.model.DiscountRule;
import com.chenbitao.noodle_shop.domain.model.Holiday;
import com.chenbitao.noodle_shop.domain.model.MenuItem;
import com.chenbitao.noodle_shop.domain.model.Money;
import com.chenbitao.noodle_shop.domain.model.Order;
import com.chenbitao.noodle_shop.exception.OrderCalculationException;
import com.chenbitao.noodle_shop.service.BillingService;
import com.chenbitao.noodle_shop.service.impl.BillingServiceImpl;
import com.chenbitao.noodle_shop.vo.DiscountResult;
import com.chenbitao.noodle_shop.vo.OrderItemRequestVO;
import com.chenbitao.noodle_shop.vo.OrderResultVO;

@Service
public class OrderService {
    private final BillingService billingService;

    public OrderService(BillingServiceImpl billingService) {
        this.billingService = billingService;
    }

    public DiscountResult calculateWithDiscount(Order order, List<DiscountRule> rules, List<MenuItem> excluded) {
        return billingService.calculateWithDiscount(order, rules, excluded);
    }

    public Money calculateWithoutDiscount(Order order) {
        return billingService.calculateWithoutDiscount(order);
    }

    public OrderResultVO dealOrder(List<OrderItemRequestVO> items) {
        try {
            Order order = new Order();
            for (OrderItemRequestVO item : items) {
                int count = item.getCount();
                if (count > 0) {
                    // 根据商品名获取 MenuItem，再添加对应数量
                    order.addItem(MenuItem.valueOf(item.getGood()), count);
                }
            }

            // 判断是否节假日
            LocalDate today = LocalDate.now();
            boolean ifHoliday = Holiday.isHoliday(today);

            // 设置打折规则
            List<DiscountRule> rules = Arrays.asList(
                    new DiscountRule(100, 15),
                    new DiscountRule(30, 5));

            if (order.getItemTotal() == 0) {
                // 如果订单为空，直接返回结果
                return new OrderResultVO(ifHoliday, Money.ZERO, Money.ZERO, rules, null);
            }
            // 不参与折扣的商品
            List<MenuItem> excluded = Arrays.asList(MenuItem.MILK_TEA);
            Money originalCost = calculateWithoutDiscount(order);
            Money cost = originalCost;
            DiscountResult discountResult = null;
            if (ifHoliday) {
                discountResult = calculateWithDiscount(order, rules, excluded);
                cost = discountResult.getFinalPrice();
            }
            // TODO 测试异常
            // throw new OrderCalculationException("计算订单价格失败");
            return new OrderResultVO(ifHoliday, originalCost, cost, rules, discountResult.getApplied());
        } catch (OrderCalculationException e) {
            throw new OrderCalculationException("计算订单价格失败", e);
        } catch (Exception e) {
            throw new RuntimeException("计算订单价格失败", e);
        }
    }
}