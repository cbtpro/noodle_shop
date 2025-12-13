package com.chenbitao.noodle_shop.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GoodsVO {

    /**
     * 用户ID - 主键
     */
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新人
     */
    private String updatedBy;
    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    /**
     * 逻辑删除字段
     * 0-未删除 1-已删除
     */
    private Integer deleted;
}
