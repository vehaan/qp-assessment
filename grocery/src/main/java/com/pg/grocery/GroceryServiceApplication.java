package com.pg.grocery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GroceryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroceryServiceApplication.class, args);
	}

}
