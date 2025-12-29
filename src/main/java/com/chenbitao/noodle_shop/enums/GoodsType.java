package com.chenbitao.noodle_shop.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 商品类型枚举
 */
@Getter
@AllArgsConstructor
public enum GoodsType {
    /**
     * 普通商品
     */
    GOOD("good", "商品", GoodsTypeCategory.GOOD),
    /**
     * 套餐商品
     */
    COMBINE("combine", "套餐", GoodsTypeCategory.COMBINE);

    private final String code;
    private final String desc;
    private final GoodsTypeCategory category;

    @JsonCreator
    public static GoodsType fromType(String type) {
        for (GoodsType gt : values()) {
            if (gt.code.equalsIgnoreCase(type)) {
                return gt;
            }
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }
}
