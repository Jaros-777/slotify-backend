package com.example.slotify_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SlotifyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlotifyBackendApplication.class, args);
	}

}
