package com.purvanovv.user_store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.purvanovv.user_store")
public class UserStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserStoreApplication.class, args);
	}

}
