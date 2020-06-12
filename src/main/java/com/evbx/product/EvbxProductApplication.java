package com.evbx.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EvbxProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvbxProductApplication.class, args);
	}

}
