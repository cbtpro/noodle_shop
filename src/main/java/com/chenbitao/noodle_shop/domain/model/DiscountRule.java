package com.chenbitao.noodle_shop.domain.model;

public class DiscountRule {
    private final int threshold;
    private final int discount;

    public DiscountRule(int threshold, int discount) {
        this.threshold = threshold;
        this.discount = discount;
    }

    public int getThreshold() { return threshold; }
    public int getDiscount() { return discount; }
}
