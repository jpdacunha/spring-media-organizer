package com.jpdacunha.media.batch.organizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringMediaBatchApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringMediaBatchApplication.class, args);
	}

}
