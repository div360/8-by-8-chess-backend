package com.mychess.mychessapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MyChessAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyChessAppApplication.class, args);
	}

}
