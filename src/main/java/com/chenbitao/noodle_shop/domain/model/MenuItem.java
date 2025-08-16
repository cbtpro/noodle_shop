package com.chenbitao.noodle_shop.domain.model;

import java.math.BigDecimal;

public enum MenuItem {
    /** 大碗牛肉面 */
    BEEF_NOODLE_LARGE(18),
    /** 中碗牛肉面 */
    BEEF_NOODLE_MEDIUM(16),
    /** 小碗牛肉面 */
    BEEF_NOODLE_SMALL(14),
    /** 大碗肥肠面 */
    INTESTINE_NOODLE_LARGE(20),
    /** 中碗肥肠面 */
    INTESTINE_NOODLE_MEDIUM(18),
    /** 小碗肥肠面 */
    INTESTINE_NOODLE_SMALL(16),
    /** 牛肉饼 */
    BEEF_CAKE(10),
    /** 奶茶 */
    MILK_TEA(12),
    /** 套餐1 */
    SET_MEAL_1(38),
    /** 套餐2 */
    SET_MEAL_2(40);

    private final BigDecimal price;

    MenuItem(int price) { this.price = BigDecimal.valueOf(price); }

    public BigDecimal getPrice() { return price; }
}
