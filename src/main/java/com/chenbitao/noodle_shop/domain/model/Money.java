package com.chenbitao.noodle_shop.domain.model;

public class Money {
    private final int amount;

    public Money(int amount) { this.amount = amount; }

    public int getAmount() { return amount; }

    public Money add(Money other) { return new Money(this.amount + other.amount); }
    public Money subtract(Money other) { return new Money(this.amount - other.amount); }

    @Override
    public String toString() { return amount + " 元"; }
}
