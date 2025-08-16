package com.chenbitao.noodle_shop.controller;

import org.springframework.web.bind.annotation.*;

import com.chenbitao.noodle_shop.application.OrderService;
import com.chenbitao.noodle_shop.domain.model.DiscountRule;
import com.chenbitao.noodle_shop.domain.model.Holiday;
import com.chenbitao.noodle_shop.domain.model.MenuItem;
import com.chenbitao.noodle_shop.domain.model.Money;
import com.chenbitao.noodle_shop.domain.model.Order;
import com.chenbitao.noodle_shop.vo.OrderResult;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService = new OrderService();

    /**
     * 计算订单价格
     *
     * @param items 订单项列表
     * @return 订单价格
     */
    @PostMapping("/calculate")
    public OrderResult calculateOrder(@RequestBody List<String> items) {
        Order order = new Order();
        for (String name : items) order.addItem(MenuItem.valueOf(name));

        List<DiscountRule> rules = Arrays.asList(
                new DiscountRule(100, 15),
                new DiscountRule(30, 5)
        );

        List<MenuItem> excluded = Arrays.asList(MenuItem.MILK_TEA);
        // 判断是否节假日
        LocalDate today = LocalDate.now();
        boolean ifHoliday = Holiday.isHoliday(today);
        Money amount = Money.ZERO;
        if (ifHoliday) {
           amount = orderService.calculateWithDiscount(order, rules, excluded);
        } else {
            amount = orderService.calculateWithoutDiscount(order);
        }
        return new OrderResult(ifHoliday, amount, rules);
    }
}
