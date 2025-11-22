package com.chenbitao.noodle_shop.noodle_shop;

import com.chenbitao.noodle_shop.application.OrderService;
import com.chenbitao.noodle_shop.domain.DiscountRule;
import com.chenbitao.noodle_shop.domain.Goods;
import com.chenbitao.noodle_shop.domain.Money;
import com.chenbitao.noodle_shop.domain.Order;
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
		Goods goods1 = Goods.builder()
				.code("combine_1")
				.name("套餐1")
				.price(BigDecimal.valueOf(38.00f))
				.build();
		order.addItem(goods1);

		Goods goods2 = Goods.builder()
				.code("beef_cake")
				.name("牛肉饼")
				.price(BigDecimal.valueOf(10.00f))
				.build();
		order.addItem(goods2);

		DiscountResult discountResult = orderService.calculateWithDiscount(order,
				Arrays.asList(new DiscountRule(100, 15), new DiscountRule(50, 5)),
				Arrays.asList("milk_tea"));
		Money total = discountResult.getFinalPrice();
		assertEquals(0, total.getAmount().compareTo(BigDecimal.valueOf(48.00)));
	}

	@Test
	public void testCalculateOrder1() {
		Order order = new Order();
		Goods goods1 = Goods.builder()
				.code("intestine_noodle_medium")
				.name("中碗肥肠面")
				.price(BigDecimal.valueOf(18.00f))
				.build();
		order.addItem(goods1);
		Goods goods2 = Goods.builder()
				.code("milk_tea")
				.name("奶茶")
				.price(BigDecimal.valueOf(12.00f))
				.build();
		order.addItem(goods2, 2);

		DiscountResult discountResult = orderService.calculateWithDiscount(order,
				Arrays.asList(new DiscountRule(100, 15),
						new DiscountRule(50, 5)),
				Arrays.asList("milk_tea"));

		assertEquals(0, discountResult.getFinalPrice().getAmount().compareTo(BigDecimal.valueOf(42.00)));
	}

	@Test
	public void testCalculateOrder2() {
		Order order = new Order();
		Goods goods1 = Goods.builder()
				.code("beef_noodle_small")
				.name("中碗肥肠面")
				.price(BigDecimal.valueOf(18.00f))
				.build();
		order.addItem(goods1, 3);
		Goods goods2 = Goods.builder()
				.code("milk_tea")
				.name("奶茶")
				.price(BigDecimal.valueOf(12.00f))
				.build();
		order.addItem(goods2, 2);

		DiscountResult discountResult = orderService.calculateWithDiscount(order,
				Arrays.asList(new DiscountRule(100, 15),
						new DiscountRule(50, 5)),
				Arrays.asList("milk_tea"));

		assertEquals(0, discountResult.getFinalPrice().getAmount().compareTo(BigDecimal.valueOf(73)));
	}

	@Test
	public void testNoDiscountApplied() {
		Order order = new Order();
		Goods goods1 = Goods.builder()
				.code("beef_noodle_small")
				.name("小碗牛肉面")
				.price(BigDecimal.valueOf(14.00f))
				.build();
		order.addItem(goods1); // 14元
		Goods goods2 = Goods.builder()
				.code("milk_tea")
				.name("奶茶")
				.price(BigDecimal.valueOf(12.00f))
				.build();
		order.addItem(goods2); // 12元

		DiscountResult discountResult = orderService.calculateWithDiscount(order,
				Arrays.asList(new DiscountRule(50, 5)), // 阈值50，实际消费26
				Arrays.asList("milk_tea"));

		// 总价应为26，不打折
		assertEquals(0, discountResult.getFinalPrice().getAmount().compareTo(BigDecimal.valueOf(26.00)));
	}

	@Test
	public void testDiscountExactlyAtThreshold() {
		Order order = new Order();
		Goods goods1 = Goods.builder()
				.code("beef_noodle_large")
				.name("大碗牛肉面")
				.price(BigDecimal.valueOf(18.00f))
				.build();
		order.addItem(goods1); // 18元

		Goods goods2 = Goods.builder()
				.code("intestine_noodle_small")
				.name("小碗肥肠面")
				.price(BigDecimal.valueOf(16.00f))
				.build();
		order.addItem(goods2); // 16元

		Goods goods3 = Goods.builder()
				.code("beef_cake")
				.name("牛肉饼")
				.price(BigDecimal.valueOf(10.00f))
				.build();
		order.addItem(goods3); // 10元

		// 总价44，满足50阈值吗？不满足
		DiscountResult discountResult = orderService.calculateWithDiscount(order,
				Arrays.asList(new DiscountRule(44, 4)), // 恰好满44减4
				Arrays.asList());

		assertEquals(0, discountResult.getFinalPrice().getAmount().compareTo(BigDecimal.valueOf(40.00))); // 44-4
	}

	@Test
	public void testMultipleItemsExcluded() {
		Order order = new Order();
		Goods goods1 = Goods.builder()
				.code("intestine_noodle_medium")
				.name("中碗肥肠面")
				.price(BigDecimal.valueOf(18.00f))
				.build();
		order.addItem(goods1, 2); // 36元

		Goods goods2 = Goods.builder()
				.code("milk_tea")
				.name("奶茶")
				.price(BigDecimal.valueOf(12.00f))
				.build();
		order.addItem(goods2, 2); // 24元

		DiscountResult discountResult = orderService.calculateWithDiscount(order,
				Arrays.asList(new DiscountRule(50, 10)),
				Arrays.asList("milk_tea")); // 奶茶不参与满减

		// discountBase = 36, 不满足50阈值 -> 不打折
		assertEquals(0, discountResult.getFinalPrice().getAmount().compareTo(BigDecimal.valueOf(60.00)));
	}

	@Test
	public void testLargeQuantityDiscount() {
		Order order = new Order();
		Goods goods1 = Goods.builder()
				.code("beef_noodle_large")
				.name("大碗牛肉面")
				.price(BigDecimal.valueOf(18.00f))
				.build();
		order.addItem(goods1, 5); // 90元

		Goods goods2 = Goods.builder()
				.code("milk_tea")
				.name("奶茶")
				.price(BigDecimal.valueOf(12.00f))
				.build();
		order.addItem(goods2, 2); // 24元

		DiscountResult discountResult = orderService.calculateWithDiscount(order,
				Arrays.asList(new DiscountRule(100, 20)), // 满100减20
				Arrays.asList("milk_tea"));

		// discountBase = 90, 不满足100阈值 -> 不打折
		assertEquals(0, discountResult.getFinalPrice().getAmount().compareTo(BigDecimal.valueOf(114)));
	}

	@Test
	public void testAllExcludedItems() {
		Order order = new Order();
		Goods good1 = Goods.builder()
				.code("beef_cake")
				.name("牛肉饼")
				.price(BigDecimal.valueOf(10.00f))
				.build();
		order.addItem(good1, 3); // 30元
		Goods goods2 = Goods.builder()
				.code("milk_tea")
				.name("奶茶")
				.price(BigDecimal.valueOf(12.00f))
				.build();
		order.addItem(goods2, 2); // 24元

		DiscountResult discountResult = orderService.calculateWithDiscount(order,
				Arrays.asList(new DiscountRule(20, 5)), // 满20减5
				Arrays.asList("beef_cake", "milk_tea"));

		// 所有商品都被排除，discountBase=0 -> 不打折
		assertEquals(0, discountResult.getFinalPrice().getAmount().compareTo(BigDecimal.valueOf(54.00)));
	}
}
