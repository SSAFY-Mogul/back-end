package com.mogul.demo;

import com.mogul.demo.webtoon.controller.WebtoonController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class MogulApplication {

	public static void main(String[] args) {
		SpringApplication.run(MogulApplication.class, args);
	}

}
