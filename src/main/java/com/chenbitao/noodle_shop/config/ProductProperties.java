package com.chenbitao.noodle_shop.config;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.chenbitao.noodle_shop.domain.model.Combine;
import com.chenbitao.noodle_shop.domain.model.Goods;

import jakarta.annotation.PostConstruct;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 将商品信息配置到配置文件中,
 * 正确的做法应该是存到数据库里
 * 但这里为了能达到快速演示,忽略数据的持久化
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "product")
public class ProductProperties {

    /** 商品列表 **/
    private List<Goods> goods;

    /** 套餐列表 **/
    private List<Combine> combine;

    /**
     * 不打折商品id列表
     */
    private List<String> nonDiscountGoods;

    @PostConstruct
    public void init() {
        Map<String, Goods> goodsMap = goods.stream().collect(Collectors.toMap(Goods::getId, g -> g));
        for (Combine combine : combine) {
            List<Goods> mappedGoods = combine.getItems().stream()
                    .map(goodsMap::get)
                    .filter(Objects::nonNull)
                    .toList();
            combine.setGoodsList(mappedGoods);
        }

    }
}