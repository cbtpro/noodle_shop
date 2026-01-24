package com.chenbitao.noodle_shop.service;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenbitao.noodle_shop.domain.base.BaseEntity;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class BaseService<T extends BaseEntity<?>> {

    private BaseMapper<T> baseMapper;

    public boolean logicDeleteById(Long id) {
        T entity = baseMapper.selectById(id);
        if (entity == null) {
            return false;
        }
        entity.setDeletedBy(getCurrentUsername());
        entity.setDeletedTime(LocalDateTime.now());
        entity.setDeleted(1);
        return baseMapper.updateById(entity) > 0;
    }

    private String getCurrentUsername() {
        // 从上下文或 Security 获取当前登录用户
        return "system";
    }
}

