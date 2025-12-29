package com.chenbitao.noodle_shop.application;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenbitao.noodle_shop.config.OrderCombineConfig;
import com.chenbitao.noodle_shop.domain.*;
import com.chenbitao.noodle_shop.enums.GoodsType;
import com.chenbitao.noodle_shop.exception.OrderCalculationException;
import com.chenbitao.noodle_shop.mapper.CombineMapper;
import com.chenbitao.noodle_shop.mapper.GoodsMapper;
import com.chenbitao.noodle_shop.mapper.NonDiscountGoodsMapper;
import com.chenbitao.noodle_shop.service.IBillingService;
import com.chenbitao.noodle_shop.service.impl.BillingServiceImpl;
import com.chenbitao.noodle_shop.vo.DiscountResult;
import com.chenbitao.noodle_shop.vo.OrderItemRequestVO;
import com.chenbitao.noodle_shop.vo.OrderItemVO;
import com.chenbitao.noodle_shop.vo.OrderResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    private final IBillingService billingService;
    private final CombineMapper combineMapper;
    private final GoodsMapper goodsMapper;
    private final NonDiscountGoodsMapper nonDiscountGoodsMapper;
    /**
     * 订单合并配置
     */
    @Autowired
    private OrderCombineConfig orderCombineConfig;

    public OrderService(BillingServiceImpl billingService, CombineMapper combineMapper, GoodsMapper goodsMapper, NonDiscountGoodsMapper nonDiscountGoodsMapper) {
        this.billingService = billingService;
        this.combineMapper = combineMapper;
        this.goodsMapper = goodsMapper;
        this.nonDiscountGoodsMapper = nonDiscountGoodsMapper;
    }

    public DiscountResult calculateWithDiscount(Order order, List<DiscountRule> rules, List<NonDiscountGoods> excludedCode) {
        log.debug("开始计算折扣: excluded={}, rules={}", excludedCode, rules);
        return billingService.calculateWithDiscount(order, rules, excludedCode);
    }

    public Money calculateWithoutDiscount(Order order) {
        log.debug("开始计算订单原价...");
        return billingService.calculateWithoutDiscount(order);
    }

    public OrderResultVO dealOrder(List<OrderItemRequestVO> items) {
        log.info("收到下单请求: {}", items);
        try {
            List<Goods> goods = this.goodsMapper.selectAllGoods();
            List<Combine> combines = this.combineMapper.selectAllWithItemsAndGoods();
            Order order = new Order();
            for (OrderItemRequestVO item : items) {
                log.debug("处理订单项: {}", item);
                int count = item.getCount();
                if (count > 0) {
                    GoodsType goodType = item.getType();
                    if (goodType == GoodsType.GOOD) {
                        // 处理普通商品
                        Goods good = goods.stream().filter(g -> g.getCode().equals(item.getCode())).findFirst().orElseThrow(() -> new OrderCalculationException("商品不存在: " + item.getGoodName()));
                        // 根据商品名获取 Goods，再添加对应数量
                        log.debug("添加商品到订单: {} x {}", good.getName(), count);
                        order.addItem(good, count);
                    } else if (goodType == GoodsType.COMBINE) {
                        // 处理套餐
                        Combine combine = combines.stream().filter(sm -> sm.getCode().equals(item.getCode())).findFirst().orElseThrow(() -> new OrderCalculationException("套餐不存在: " + item.getGoodName()));

                        log.debug("添加套餐到订单: {} x {}", combine.getName(), count);
                        order.addItem(combine, count);
                    }
                }
            }

            // 匹配套餐,将订单中符合套餐的商品进行组合
            if (orderCombineConfig.isAuto()) {
                log.debug("自动匹配套餐规则已开启，尝试根据组合规则调整订单...");
                billingService.matchSetMeals(order, combines);
            }

            // 判断是否节假日
            LocalDate today = LocalDate.now();
            boolean ifHoliday = Holiday.isHoliday(today);
            log.info("今日日期: {}, 是否节假日: {}", today, ifHoliday);

            // 设置打折规则
            List<DiscountRule> rules = Arrays.asList(
                    new DiscountRule(100, 15),
                    new DiscountRule(30, 5)
            );

            if (order.getItemTotal() == 0) {
                log.warn("订单为空，直接返回");
                // 如果订单为空，直接返回结果
                return new OrderResultVO(ifHoliday, Money.ZERO, Money.ZERO, null, rules, null);
            }
            // 不参与折扣的商品
            List<NonDiscountGoods> excludedCodes = nonDiscountGoodsMapper.selectList(new QueryWrapper<NonDiscountGoods>());
            log.debug("不参与折扣的商品code列表: {}", excludedCodes);
            Money originalCost = calculateWithoutDiscount(order);
            log.info("订单原价计算完成: {}", originalCost);

            Money finalCost = originalCost;
            List<DiscountRule> appliedRules = null;

            if (ifHoliday) {
                log.debug("开始节假日折扣计算...");
                DiscountResult discountResult = calculateWithDiscount(order, rules, excludedCodes);

                finalCost = discountResult.getFinalPrice();
                appliedRules = discountResult.getApplied();
                log.info("折扣计算完成: 最终价格={}, 使用规则={}", finalCost, appliedRules);
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
            log.info("订单计算完成，返回结果");
            return new OrderResultVO(
                    ifHoliday,
                    originalCost,
                    finalCost,
                    itemVOs,
                    rules,
                    appliedRules
            );
        } catch (OrderCalculationException e) {
            log.error("订单业务异常: {}", e.getMessage(), e);
            throw new OrderCalculationException("计算订单价格失败", e);
        } catch (Exception e) {
            log.error("订单计算发生未知异常", e);
            throw new RuntimeException("计算订单价格失败", e);
        }
    }
}