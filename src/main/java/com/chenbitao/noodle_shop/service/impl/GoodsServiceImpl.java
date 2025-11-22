package com.chenbitao.noodle_shop.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenbitao.noodle_shop.domain.Goods;
import com.chenbitao.noodle_shop.mapper.GoodsMapper;
import com.chenbitao.noodle_shop.service.IGoodsService;
import com.chenbitao.noodle_shop.vo.GoodsVO;

public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    /**
     * 自定义方法：根据商品名称查询商品
     */

    @DS("cluster1")
    public Goods getUserByUsername(String goodName) {
        // 使用 MP 的查询构造器
        return lambdaQuery().like(Goods::getName, goodName)
                .one();
    }

    @Override
    @Transactional
    public boolean updateById(Long id, Map<String, Object> updates) {
        if (id == null || updates == null || updates.isEmpty()) {
            return false;
        }

        UpdateWrapper<Goods> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id); // 按 ID 条件

        // 设置更新字段
        updates.forEach(wrapper::set);

        // 执行更新
        return this.update(wrapper);
    }

    @Override
    public GoodsVO getGoodsById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGoodsById'");
    }

    @Override
    public String addGoods(Goods goods) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addGoods'");
    }

    @Override
    public String addGoods(List<Goods> goods) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addGoods'");
    }

    @Override
    public String saveOrUpdateGoods(Goods goods) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveOrUpdateGoods'");
    }

    @Override
    public Page<GoodsVO> queryGoodsPage(Page<Goods> page) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'queryGoodsPage'");
    }

    @Override
    public String deleteGoodsById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteGoodsById'");
    }

    @Override
    public String batchDeleteGoodsByIds(List<Long> ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'batchDeleteGoodsByIds'");
    }

    @Override
    public List<Goods> getAdultGoods() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAdultGoods'");
    }

    @Override
    @DS("cluster1")
    public List<Goods> getComplexGoods() {
        return this.lambdaQuery()
                .between(Goods::getPrice, 18, 60)
                .orderByDesc(Goods::getPrice)
                .list();
    }

    @Override
    @DS("cluster1")
    public Page<GoodsVO> getGoodsPageWithCondition(Integer current, Integer size, String keyword) {
        Page<Goods> page = new Page<>(current, size);
        Page<Goods> records = this.lambdaQuery()
                .like(StringUtils.isNotBlank(keyword), Goods::getName, keyword)
                .page(page);

        // 将转换后的数据设置回分页对象
        Page<GoodsVO> voPage = new Page<>();
        voPage.setCurrent(records.getCurrent());
        voPage.setSize(records.getSize());
        voPage.setTotal(records.getTotal());
        voPage.setRecords(records.getRecords().stream()
                .map(goods -> GoodsVO.builder()
                        .id(goods.getId())
                        .name(goods.getName())
                        .price(goods.getPrice())
                        .createdTime(goods.getCreatedTime())
                        .updatedTime(goods.getUpdatedTime())
                        .deleted(goods.getDeleted())
                        .build())
                .toList());
        return voPage;
    }

    @Override
    @Transactional
    public String partialUpdateGoods(Long id, Map<String, Object> updates) {
        // 移除不能更新的字段
        updates.remove("id");
        updates.remove("createTime");
        updates.remove("deleted");

        boolean success = this.updateById(id, updates);
        return success ? "部分更新成功" : "部分更新失败";
    }

    @Override
    @Transactional
    public String updateGoods(Goods goods) {
        if (goods.getId() == null) {
            return "商品ID不能为空";
        }

        boolean success = this.updateById(goods);

        return success ? "更新成功" : "更新失败";
    }
}
