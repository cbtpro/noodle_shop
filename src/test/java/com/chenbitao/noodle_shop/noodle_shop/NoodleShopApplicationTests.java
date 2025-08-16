package com.chenbitao.noodle_shop.noodle_shop;

import com.chenbitao.noodle_shop.application.OrderService;
import com.chenbitao.noodle_shop.domain.model.DiscountRule;
import com.chenbitao.noodle_shop.domain.model.MenuItem;
import com.chenbitao.noodle_shop.domain.model.Money;
import com.chenbitao.noodle_shop.domain.model.Order;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class NoodleShopApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testCalculateOrder() {
		OrderService orderService = new OrderService();
		Order order = new Order();
		order.addItem(MenuItem.SET_MEAL_1);
		order.addItem(MenuItem.BEEF_CAKE);

		Money total = orderService.calculateOrder(order,
				Arrays.asList(new DiscountRule(100, 15), new DiscountRule(50, 5)),
				Arrays.asList(MenuItem.MILK_TEA));

		assertEquals(48, total.getAmount());
	}


	@Test
	public void testCalculateOrder1() {
		OrderService orderService = new OrderService();
		Order order = new Order();
		order.addItem(MenuItem.INTESTINE_NOODLE_MEDIUM);
		order.addItem(MenuItem.MILK_TEA, 2);

		Money total = orderService.calculateOrder(order,
				Arrays.asList(new DiscountRule(100, 15), new DiscountRule(50, 5)),
				Arrays.asList(MenuItem.MILK_TEA));

		assertEquals(42, total.getAmount());
	}
}
