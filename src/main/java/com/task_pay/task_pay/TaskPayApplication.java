package com.task_pay.task_pay;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class TaskPayApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(TaskPayApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
