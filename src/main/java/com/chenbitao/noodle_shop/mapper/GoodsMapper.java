package com.chenbitao.noodle_shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenbitao.noodle_shop.domain.Goods;
//import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 查询价格大于指定值的商品列表
     */
    List<Goods> selectGoodsByMinPrice(@Param("minPrice") Integer minPrice);

    /**
     * 查询所有未删除的商品
     */
    List<Goods> selectAllGoods();
}
