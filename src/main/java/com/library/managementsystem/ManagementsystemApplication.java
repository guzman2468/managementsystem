package com.library.managementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ManagementsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagementsystemApplication.class, args);
	}

    @GetMapping
    public String helloWorld() {
        return "Hello World Spring boot";
    }
}
