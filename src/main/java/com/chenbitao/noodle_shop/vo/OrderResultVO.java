package com.chenbitao.noodle_shop.vo;

import java.util.List;

import com.chenbitao.noodle_shop.domain.DiscountRule;
import com.chenbitao.noodle_shop.domain.Money;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResultVO {
    /**
     * 是否节假日
     */
    private boolean holiday;
    /**
     * 原价
     */
    private Money originalCost;
    /**
     * 最终价格
     */
    private Money cost;
    /**
     * 订单列表
     */
    private List<OrderItemVO> items;
    /**
     * 可用的折扣规则
     */
    private List<DiscountRule> rules;
    /**
     * 生效的折扣规则
     */
    private List<DiscountRule> appliedRules;

}