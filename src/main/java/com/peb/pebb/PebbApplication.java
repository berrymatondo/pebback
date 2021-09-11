package com.peb.pebb;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class PebbApplication {

	public static void main(String[] args) {
		SpringApplication.run(PebbApplication.class, args);
	}

	@Scheduled(initialDelay = 2000L, fixedDelayString = "${someJob.delay}") // toutesles 2 secondes
	void someJob() throws Exception {
		System.out.println("Now is := " + new Date());
		Thread.sleep(1000L);
	}

}

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
class scheduleConfig {
}
