package com.chenbitao.noodle_shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chenbitao.noodle_shop.mapper")
public class NoodleShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoodleShopApplication.class, args);
	}

}
