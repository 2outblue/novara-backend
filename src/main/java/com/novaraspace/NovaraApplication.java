package com.novaraspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class NovaraApplication {

	public static void main(String[] args) {
		SpringApplication.run(NovaraApplication.class, args);
	}

}
