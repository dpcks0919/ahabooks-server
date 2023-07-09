package com.waywalkers.kbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableScheduling
@SpringBootApplication
public class KbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(KbookApplication.class, args);
	}

}
