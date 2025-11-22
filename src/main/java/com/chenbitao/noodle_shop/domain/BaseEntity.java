package com.chenbitao.noodle_shop.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 所有实体类的通用基类
 */
@Data
public abstract class BaseEntity<ID extends Serializable> implements Serializable {

    /** 主键ID */
    @TableId(value = "id", type = IdType.ASSIGN_ID) // 或 IdType.AUTO
    private Long id;

    /** 乐观锁版本号 */
    @Version
    private Integer revision;

    /** 创建人 */
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /** 更新人 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;

    /**
     * 逻辑删除字段
     * 0-未删除 1-已删除
     */
    @TableLogic
    private Integer deleted;

    /** 删除人 */
    @TableField(fill = FieldFill.UPDATE)
    private String deletedBy;

    /** 删除时间 */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime deletedTime;
}
