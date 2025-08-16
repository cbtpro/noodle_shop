package com.chenbitao.noodle_shop.domain.model;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private final Map<MenuItem, Integer> items = new HashMap<>();

    public void addItem(MenuItem item) {
        addItem(item, 1);
    }

    public void addItem(MenuItem item, int count) {
        items.put(item, items.getOrDefault(item, 0) + count);
    }

    /** 移除商品（用于套餐匹配） */
    public void removeItem(MenuItem item, int count) {
        int current = items.getOrDefault(item, 0);
        if (current <= count) {
            items.remove(item);
        } else {
            items.put(item, current - count);
        }
    }

    public int getItemTotal() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }
    /** 获取某个商品的数量 */
    public int getItemCount(MenuItem item) {
        return items.getOrDefault(item, 0);
    }

    /** 获取订单中所有商品 */
    public Map<MenuItem, Integer> getItems() {
        return items;
    }
}