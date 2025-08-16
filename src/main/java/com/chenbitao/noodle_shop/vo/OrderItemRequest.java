package com.chenbitao.noodle_shop.vo;

public class OrderItemRequest {
    private String good;
    private int count;

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
