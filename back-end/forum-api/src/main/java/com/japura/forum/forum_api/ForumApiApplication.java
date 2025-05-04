package com.japura.forum.forum_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

@SpringBootApplication(exclude = { WebMvcAutoConfiguration.class })
public class ForumApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumApiApplication.class, args);
	}

}
