package com.chenbitao.noodle_shop.domain.model;

import java.util.HashMap;
import java.util.Map;

public class SetMeal {
    private final String name;
    private final Map<Goods, Integer> items = new HashMap<>();
    private final Money price;

    public SetMeal(String name, Money price) {
        this.name = name;
        this.price = price;
    }

    /** 添加套餐里的商品和数量 */
    public void addItem(Goods item, int count) {
        items.put(item, count);
    }

    /** 获取套餐名称 */
    public String getName() {
        return name;
    }

    /** 获取套餐价格 */
    public Money getPrice() {
        return price;
    }

    /** 获取套餐内所有商品和数量 */
    public Map<Goods, Integer> getItems() {
        return items;
    }

    /** 判断订单是否能匹配这个套餐 */
    public boolean canApply(Order order) {
        for (Map.Entry<Goods, Integer> entry : items.entrySet()) {
            if (order.getItemCount(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    /** 从订单中扣除套餐里的商品 */
    public void applyTo(Order order) {
        for (Map.Entry<Goods, Integer> entry : items.entrySet()) {
            order.removeItem(entry.getKey(), entry.getValue());
        }
    }
}