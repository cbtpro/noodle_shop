package com.chenbitao.noodle_shop.mapper;

import java.util.List;

//import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenbitao.noodle_shop.domain.Combine;

//@Mapper
public interface CombineMapper extends BaseMapper<Combine> {

    /**
     * 查询所有套餐，并包含套餐明细和商品信息
     */
    List<Combine> selectAllWithItemsAndGoods();

    /**
     * 根据ID查询套餐，并包含明细和商品信息
     */
    Combine selectByIdWithItemsAndGoods(@Param("id") Long id);
}
