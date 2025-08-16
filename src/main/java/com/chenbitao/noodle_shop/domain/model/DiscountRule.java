package com.chenbitao.noodle_shop.domain.model;

import java.math.BigDecimal;

/**
 * 折扣规则
 */
public class DiscountRule {
    private final BigDecimal threshold;
    private final BigDecimal discount;

    public DiscountRule(int threshold, int discount) {
        this.threshold = BigDecimal.valueOf(threshold);
        this.discount = BigDecimal.valueOf(discount);
    }
    public DiscountRule(float threshold, float discount) {
        this.threshold = BigDecimal.valueOf(threshold);
        this.discount = BigDecimal.valueOf(discount);
    }

    public BigDecimal getThreshold() { return threshold; }
    public BigDecimal getDiscount() { return discount; }
}
