package com.chenbitao.noodle_shop.controller;

import org.springframework.web.bind.annotation.*;

import com.chenbitao.noodle_shop.application.OrderService;
import com.chenbitao.noodle_shop.vo.OrderItemRequestVO;
import com.chenbitao.noodle_shop.vo.OrderResultVO;

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
        return orderService.calc(items);
    }
}
