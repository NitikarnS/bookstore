package com.project.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Component
@EnableScheduling
@EnableSwagger2
@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner {

	@Autowired
	UpdateBookTask updateBookTask;

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (updateBookTask.shouldUpdateBook()) {
			updateBookTask.updateBooks();
		}
	}

}
