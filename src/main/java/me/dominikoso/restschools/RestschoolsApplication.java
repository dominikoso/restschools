package me.dominikoso.restschools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RestschoolsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestschoolsApplication.class, args);
	}

}
