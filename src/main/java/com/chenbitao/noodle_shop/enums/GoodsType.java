package com.chenbitao.noodle_shop.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 商品类型枚举
 */
public enum GoodsType {
    /**
     * 普通商品
     */
    GOOD("good"),
    /**
     * 套餐商品
     */
    COMBINE("combine");

    private final String type;

    GoodsType(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return type;
    }

    @JsonCreator
    public static GoodsType fromType(String type) {
        for (GoodsType gt : values()) {
            if (gt.type.equalsIgnoreCase(type)) {
                return gt;
            }
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }
}
