package com.chenbitao.noodle_shop.application;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenbitao.noodle_shop.config.OrderCombineConfig;
import com.chenbitao.noodle_shop.config.ProductProperties;
import com.chenbitao.noodle_shop.domain.model.*;
import com.chenbitao.noodle_shop.enums.GoodsType;
import com.chenbitao.noodle_shop.exception.OrderCalculationException;
import com.chenbitao.noodle_shop.service.IBillingService;
import com.chenbitao.noodle_shop.service.impl.BillingServiceImpl;
import com.chenbitao.noodle_shop.vo.DiscountResult;
import com.chenbitao.noodle_shop.vo.OrderItemRequestVO;
import com.chenbitao.noodle_shop.vo.OrderItemVO;
import com.chenbitao.noodle_shop.vo.OrderResultVO;

@Service
public class OrderService {
    private final IBillingService billingService;

    /**
     * 订单合并配置
     */
    @Autowired
    private OrderCombineConfig orderCombineConfig;

    @Autowired
    private ProductProperties productProperties;

    public OrderService(BillingServiceImpl billingService) {
        this.billingService = billingService;
    }

    public DiscountResult calculateWithDiscount(Order order, List<DiscountRule> rules, List<String> excluded) {
        return billingService.calculateWithDiscount(order, rules, excluded);
    }

    public Money calculateWithoutDiscount(Order order) {
        return billingService.calculateWithoutDiscount(order);
    }

    public OrderResultVO dealOrder(List<OrderItemRequestVO> items) {
        try {
            List<Goods> goods = productProperties.getGoods();
            List<Combine> combines = productProperties.getCombine();
            Order order = new Order();
            for (OrderItemRequestVO item : items) {
                int count = item.getCount();
                if (count > 0) {
                    GoodsType goodType = item.getType();
                    if (goodType == GoodsType.GOOD) {
                        // 处理普通商品
                        Goods good = goods.stream()
                                .filter(g -> g.getId().equals(item.getId()))
                                .findFirst()
                                .orElseThrow(() -> new OrderCalculationException("商品不存在: " + item.getGoodName()));
                        // 根据商品名获取 Goods，再添加对应数量
                        if (good != null) {
                            order.addItem(good, count);
                        }
                    } else if (goodType == GoodsType.COMBINE) {
                        // 处理套餐
                        Combine combine = combines.stream()
                                .filter(sm -> sm.getId().equals(item.getId()))
                                .findFirst()
                                .orElseThrow(() -> new OrderCalculationException("套餐不存在: " + item.getGoodName()));
                        if (combine != null) {
                            order.addItem(combine, count);
                        }
                    }
                }
            }

            // 匹配套餐,将订单中符合套餐的商品进行组合
            if (orderCombineConfig.isAuto()) {
                billingService.matchSetMeals(order, combines);
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
                return new OrderResultVO(ifHoliday, Money.ZERO, Money.ZERO, null, rules, null);
            }
            // 不参与折扣的商品
            List<String> excludedIds = productProperties.getNonDiscountGoods();
            Money originalCost = calculateWithoutDiscount(order);
            Money cost = originalCost;
            DiscountResult discountResult = null;
            if (ifHoliday) {
                discountResult = calculateWithDiscount(order, rules, excludedIds);
                cost = discountResult.getFinalPrice();
            }
            // 测试异常情况
            // throw new OrderCalculationException("计算订单价格失败");
            List<OrderItemVO> itemVOs = order.getItems().entrySet().stream()
                    .map(e -> new OrderItemVO(
                            e.getKey().getId(),
                            e.getKey().getName(),
                            new Money(e.getKey().getPrice()),
                            e.getValue()))
                    .collect(Collectors.toList());
            return new OrderResultVO(ifHoliday, originalCost, cost, itemVOs, rules, discountResult.getApplied());
        } catch (OrderCalculationException e) {
            throw new OrderCalculationException("计算订单价格失败", e);
        } catch (Exception e) {
            throw new RuntimeException("计算订单价格失败", e);
        }
    }
}