package com.chenbitao.noodle_shop.controller;

import org.springframework.web.bind.annotation.*;

import com.chenbitao.noodle_shop.application.OrderService;
import com.chenbitao.noodle_shop.domain.model.DiscountRule;
import com.chenbitao.noodle_shop.domain.model.MenuItem;
import com.chenbitao.noodle_shop.domain.model.Money;
import com.chenbitao.noodle_shop.domain.model.Order;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService = new OrderService();

    @PostMapping("/calculate")
    public Money calculateOrder(@RequestBody List<String> items) {
        Order order = new Order();
        for (String name : items) order.addItem(MenuItem.valueOf(name));

        List<DiscountRule> rules = Arrays.asList(
                new DiscountRule(100, 15),
                new DiscountRule(50, 5)
        );

        List<MenuItem> excluded = Arrays.asList(MenuItem.MILK_TEA);

        return orderService.calculateOrder(order, rules, excluded);
    }
}
