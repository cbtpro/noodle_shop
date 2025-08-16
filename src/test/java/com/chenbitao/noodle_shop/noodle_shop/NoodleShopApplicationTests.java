package com.chenbitao.noodle_shop.noodle_shop;

import com.chenbitao.noodle_shop.application.OrderService;
import com.chenbitao.noodle_shop.domain.model.DiscountRule;
import com.chenbitao.noodle_shop.domain.model.MenuItem;
import com.chenbitao.noodle_shop.domain.model.Money;
import com.chenbitao.noodle_shop.domain.model.Order;
import com.chenbitao.noodle_shop.service.impl.BillingServiceImpl;
import com.chenbitao.noodle_shop.vo.DiscountResult;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class NoodleShopApplicationTests {

	@Autowired
    private OrderService orderService;

	@Test
	void contextLoads() {
	}

	@Test
	public void testCalculateOrder() {
		Order order = new Order();
		order.addItem(MenuItem.SET_MEAL_1);
		order.addItem(MenuItem.BEEF_CAKE);

		DiscountResult discountResult = orderService.calculateWithDiscount(order,
				Arrays.asList(new DiscountRule(100, 15), new DiscountRule(50, 5)),
				Arrays.asList(MenuItem.MILK_TEA));
		Money total = discountResult.getFinalPrice();
		assertEquals(0, total.getAmount().compareTo(BigDecimal.valueOf(48.00)));
	}

	@Test
	public void testCalculateOrder1() {
		Order order = new Order();
		order.addItem(MenuItem.INTESTINE_NOODLE_MEDIUM);
		order.addItem(MenuItem.MILK_TEA, 2);

		DiscountResult discountResult = orderService.calculateWithDiscount(order,
				Arrays.asList(new DiscountRule(100, 15),
						new DiscountRule(50, 5)),
				Arrays.asList(MenuItem.MILK_TEA));

		assertEquals(0, discountResult.getFinalPrice().getAmount().compareTo(BigDecimal.valueOf(42.00)));
	}

	@Test
	public void testCalculateOrder2() {
		Order order = new Order();
		order.addItem(MenuItem.INTESTINE_NOODLE_MEDIUM, 3);
		order.addItem(MenuItem.MILK_TEA, 2);

		DiscountResult discountResult = orderService.calculateWithDiscount(order,
				Arrays.asList(new DiscountRule(100, 15),
						new DiscountRule(50, 5)),
				Arrays.asList(MenuItem.MILK_TEA));

		assertEquals(0, discountResult.getFinalPrice().getAmount().compareTo(BigDecimal.valueOf(73)));
	}

	@Test
	public void testNoDiscountApplied() {
		Order order = new Order();
		order.addItem(MenuItem.BEEF_NOODLE_SMALL); // 14元
		order.addItem(MenuItem.MILK_TEA); // 12元

		DiscountResult discountResult = orderService.calculateWithDiscount(order,
				Arrays.asList(new DiscountRule(50, 5)), // 阈值50，实际消费26
				Arrays.asList(MenuItem.MILK_TEA));

		// 总价应为26，不打折
		assertEquals(0, discountResult.getFinalPrice().getAmount().compareTo(BigDecimal.valueOf(26.00)));
	}

	@Test
	public void testDiscountExactlyAtThreshold() {
		Order order = new Order();
		order.addItem(MenuItem.BEEF_NOODLE_LARGE); // 18元
		order.addItem(MenuItem.INTESTINE_NOODLE_SMALL); // 16元
		order.addItem(MenuItem.BEEF_CAKE); // 10元
		// 总价44，满足50阈值吗？不满足
		DiscountResult discountResult = orderService.calculateWithDiscount(order,
				Arrays.asList(new DiscountRule(44, 4)), // 恰好满44减4
				Arrays.asList());

		assertEquals(0, discountResult.getFinalPrice().getAmount().compareTo(BigDecimal.valueOf(40.00))); // 44-4
	}

	@Test
	public void testMultipleItemsExcluded() {
		Order order = new Order();
		order.addItem(MenuItem.INTESTINE_NOODLE_MEDIUM, 2); // 36元
		order.addItem(MenuItem.MILK_TEA, 2); // 24元

		DiscountResult discountResult = orderService.calculateWithDiscount(order,
				Arrays.asList(new DiscountRule(50, 10)),
				Arrays.asList(MenuItem.MILK_TEA)); // 奶茶不参与满减

		// discountBase = 36, 不满足50阈值 -> 不打折
		assertEquals(0, discountResult.getFinalPrice().getAmount().compareTo(BigDecimal.valueOf(60.00)));
	}

	@Test
	public void testLargeQuantityDiscount() {
		Order order = new Order();
		order.addItem(MenuItem.BEEF_NOODLE_LARGE, 5); // 90元
		order.addItem(MenuItem.MILK_TEA, 2); // 24元

		DiscountResult discountResult = orderService.calculateWithDiscount(order,
				Arrays.asList(new DiscountRule(100, 20)), // 满100减20
				Arrays.asList(MenuItem.MILK_TEA));

		// discountBase = 90, 不满足100阈值 -> 不打折
		assertEquals(0, discountResult.getFinalPrice().getAmount().compareTo(BigDecimal.valueOf(114)));
	}

	@Test
	public void testAllExcludedItems() {
		Order order = new Order();
		order.addItem(MenuItem.BEEF_CAKE, 3); // 30元
		order.addItem(MenuItem.MILK_TEA, 2); // 24元

		DiscountResult discountResult = orderService.calculateWithDiscount(order,
				Arrays.asList(new DiscountRule(20, 5)), // 满20减5
				Arrays.asList(MenuItem.BEEF_CAKE, MenuItem.MILK_TEA));

		// 所有商品都被排除，discountBase=0 -> 不打折
		assertEquals(0, discountResult.getFinalPrice().getAmount().compareTo(BigDecimal.valueOf(54.00)));
	}
}
