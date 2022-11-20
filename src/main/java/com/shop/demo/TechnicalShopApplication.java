package com.shop.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TechnicalShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechnicalShopApplication.class, args);
	}

}
