package com.chenbitao.noodle_shop.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final List<MenuItem> items = new ArrayList<>();

    public void addItem(MenuItem item) {
        items.add(item);
    }


    public void addItem(MenuItem item, int count) {
        for (int i = 0; i < count; i++) {
            items.add(item);
        }
    }

    public List<MenuItem> getItems() {
        return new ArrayList<>(items);
    }
}