package com.chenbitao.noodle_shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenbitao.noodle_shop.domain.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    @Select("SELECT id, name, price FROM goods WHERE price > #{minPrice}")
    List<Goods> selectUsersByMinPrice(@Param("minPrice") Integer minPrice);
}