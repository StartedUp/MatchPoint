package com.matchpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.matchpoint")
@EnableAutoConfiguration
public class MatchpointApplication {
	public static void main(String[] args) {
		SpringApplication.run(MatchpointApplication.class, args);
	}
}
