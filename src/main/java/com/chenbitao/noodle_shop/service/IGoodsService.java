package com.chenbitao.noodle_shop.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenbitao.noodle_shop.domain.Goods;
import com.chenbitao.noodle_shop.vo.GoodsVO;

public interface IGoodsService extends IService<Goods> {

    /**
     * 按 ID 更新部分字段（动态更新）
     *
     * @param id      商品ID
     * @param updates 要更新的字段 Map，例如 {"price": 20, "name": "干炒牛河"}
     * @return 更新是否成功
     */
    boolean updateById(Long id, Map<String, Object> updates);

    GoodsVO getGoodsById(Long id);

    String addGoods(Goods goods);

    String addGoods(List<Goods> goods);

    String saveOrUpdateGoods(Goods goods);

    Page<GoodsVO> queryGoodsPage(Page<Goods> page);

    String deleteGoodsById(Long id);

    String batchDeleteGoodsByIds(List<Long> ids);

    Page<GoodsVO> getGoodsPageWithCondition(Integer current, Integer size, String keyword);

    String partialUpdateGoods(Long id, Map<String, Object> updates);

    String updateGoods(Goods goods);

}
