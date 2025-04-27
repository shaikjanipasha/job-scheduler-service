package com.vtx.jobscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JobSchedulerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobSchedulerServiceApplication.class, args);
	}

}
