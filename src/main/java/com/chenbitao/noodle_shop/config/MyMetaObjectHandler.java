package com.chenbitao.noodle_shop.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 公共字段自动填充处理器
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createdTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updatedTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "revision", () -> 0, Integer.class);
        this.strictInsertFill(metaObject, "createdBy", this::getCurrentUsername, String.class);
        this.strictInsertFill(metaObject, "updatedBy", this::getCurrentUsername, String.class);
        this.strictInsertFill(metaObject, "isDeleted", () -> 0, Integer.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updatedTime", LocalDateTime::now, LocalDateTime.class);
        this.strictUpdateFill(metaObject, "updatedBy", this::getCurrentUsername, String.class);
    }

    private String getCurrentUsername() {
        // TODO: 从上下文或安全框架中获取当前登录用户
        return "system";
    }
}