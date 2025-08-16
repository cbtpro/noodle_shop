package com.chenbitao.noodle_shop.controller;

import org.springframework.web.bind.annotation.*;

import com.chenbitao.noodle_shop.application.OrderService;
import com.chenbitao.noodle_shop.domain.model.DiscountRule;
import com.chenbitao.noodle_shop.domain.model.Holiday;
import com.chenbitao.noodle_shop.domain.model.MenuItem;
import com.chenbitao.noodle_shop.domain.model.Money;
import com.chenbitao.noodle_shop.domain.model.Order;
import com.chenbitao.noodle_shop.vo.OrderItemRequestVO;
import com.chenbitao.noodle_shop.vo.OrderResultVO;

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
    public OrderResultVO calculateOrder(@RequestBody List<OrderItemRequestVO> items) {
        Order order = new Order();
        for (OrderItemRequestVO item : items) {
            // 根据商品名获取 MenuItem，再添加对应数量
            order.addItem(MenuItem.valueOf(item.getGood()), item.getCount());
        }

        // 打折规则
        List<DiscountRule> rules = Arrays.asList(
                new DiscountRule(100, 15),
                new DiscountRule(30, 5));

        // 不参与折扣的商品
        List<MenuItem> excluded = Arrays.asList(MenuItem.MILK_TEA);
        // 判断是否节假日
        LocalDate today = LocalDate.now();
        boolean ifHoliday = Holiday.isHoliday(today);
        Money originalCost = orderService.calculateWithoutDiscount(order);
        Money cost = originalCost;
        if (ifHoliday) {
            cost = orderService.calculateWithDiscount(order, rules, excluded);
        }
        return new OrderResultVO(ifHoliday, originalCost, cost, rules);
    }
}
