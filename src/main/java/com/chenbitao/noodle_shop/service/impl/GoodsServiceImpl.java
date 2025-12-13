package com.chenbitao.noodle_shop.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    @DS("cluster1")
    public GoodsVO getGoodsById(Long id) {
        // if (id == null) {
        // throw new BusinessException("商品ID不能为空");
        // }

        // Goods goods = this.baseMapper.selectById(id);
        // if (goods == null) {
        // throw new BusinessException("商品不存在");
        // }
        // return GoodsVO.builder()
        // .id(goods.getId())
        // .name(goods.getName())
        // .price(goods.getPrice())
        // .createdBy(goods.getCreatedBy())
        // .createdTime(goods.getCreatedTime())
        // .updatedBy(goods.getUpdatedBy())
        // .updatedTime(goods.getUpdatedTime())
        // .build();
        return Optional.ofNullable(id)
                .map(this.baseMapper::selectById)
                .map(goods -> GoodsVO.builder()
                        .id(goods.getId())
                        .name(goods.getName())
                        .price(goods.getPrice())
                        .createdBy(goods.getCreatedBy())
                        .createdTime(goods.getCreatedTime())
                        .updatedBy(goods.getUpdatedBy())
                        .updatedTime(goods.getUpdatedTime())
                        .build())
                .orElseThrow(() -> new RuntimeException("商品不存在或ID为空"));
    }

    @Override
    public String addGoods(Goods goods) {
        return Optional.ofNullable(goods)
                .filter(g -> this.baseMapper.insert(g) > 0)
                .map(g -> "新增商品成功")
                .orElseThrow(() -> new RuntimeException("新增商品失败，商品不能为空"));
    }

    @Override
    public String addGoods(List<Goods> goodsList) {
        if (goodsList == null || goodsList.isEmpty()) {
            throw new RuntimeException("商品列表不能为空");
        }

        boolean success = goodsList.stream()
                .allMatch(goods -> this.baseMapper.insert(goods) > 0);

        if (!success) {
            throw new RuntimeException("批量新增商品失败");
        }
        return "批量新增商品成功";
    }

    @Override
    public String saveOrUpdateGoods(Goods goods) {
        return Optional.ofNullable(goods)
                .map(g -> {
                    if (g.getId() == null) {
                        return this.baseMapper.insert(g);
                    }
                    return this.baseMapper.updateById(g);
                })
                .filter(result -> result > 0)
                .map(r -> "保存或更新商品成功")
                .orElseThrow(() -> new RuntimeException("保存或更新商品失败"));
    }

    @Override
    @DS("cluster1")
    public Page<GoodsVO> queryGoodsPage(Page<Goods> page) {
        if (page == null) {
            throw new RuntimeException("分页参数不能为空");
        }

        Page<Goods> goodsPage = this.baseMapper.selectPage(page, null);

        Page<GoodsVO> voPage = new Page<>();
        voPage.setCurrent(goodsPage.getCurrent());
        voPage.setSize(goodsPage.getSize());
        voPage.setTotal(goodsPage.getTotal());

        List<GoodsVO> records = goodsPage.getRecords().stream()
                .map(goods -> GoodsVO.builder()
                        .id(goods.getId())
                        .name(goods.getName())
                        .price(goods.getPrice())
                        .createdBy(goods.getCreatedBy())
                        .createdTime(goods.getCreatedTime())
                        .updatedBy(goods.getUpdatedBy())
                        .updatedTime(goods.getUpdatedTime())
                        .build())
                .toList();

        voPage.setRecords(records);
        return voPage;
    }

    @Override
    public String deleteGoodsById(Long id) {
        return Optional.ofNullable(id)
                .filter(gid -> this.baseMapper.deleteById(gid) > 0)
                .map(gid -> "删除商品成功")
                .orElseThrow(() -> new RuntimeException("删除商品失败，ID为空或商品不存在"));
    }

    @Override
    public String batchDeleteGoodsByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new RuntimeException("商品ID列表不能为空");
        }

        boolean success = this.removeByIds(ids);
        if (!success) {
            throw new RuntimeException("批量删除商品失败");
        }
        return "批量删除商品成功";
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
