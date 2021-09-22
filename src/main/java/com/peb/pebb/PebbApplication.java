package com.peb.pebb;

import java.util.ArrayList;
import java.util.Date;

import com.peb.pebb.appUser.AppUser;
import com.peb.pebb.appUser.AppUserRole;
import com.peb.pebb.appUser.AppUserService;
import com.peb.pebb.resume.Resume;
import com.peb.pebb.role.Role;
import com.peb.pebb.role.RoleRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PebbApplication {

	public static void main(String[] args) {
		SpringApplication.run(PebbApplication.class, args);
	}

	@Bean
	CommandLineRunner run(AppUserService appUserService, RoleRepository roleRepository) {
		return args -> {

			appUserService.saveRole(new Role(1L, "ROLE_USER"));
			appUserService.saveRole(new Role(2L, "ROLE_ADMIN"));

			/*
			 * appUserService .saveAppUsers(new AppUser("Rivo", "Spinda",
			 * "jmatondo1@hotmail.com", "456", new ArrayList<>()));
			 * 
			 * appUserService .saveAppUsers(new AppUser("Berry", "Matondo",
			 * "jmatondo@hotmail.com", "123", new ArrayList<>()));
			 * 
			 * appUserService.addRoleToUser("jmatondo1@hotmail.com", "ROLE_USER");
			 * appUserService.addRoleToUser("jmatondo@hotmail.com", "ROLE_USER");
			 * appUserService.addRoleToUser("jmatondo@hotmail.com", "ROLE_ADMIN");
			 */

			RestTemplate restTemplate = new RestTemplate();
			// Resume resume =
			// restTemplate.getForObject("http://localhost:9050/peb/resumes/1",
			// Resume.class);
			// Resume resume =
			// restTemplate.getForObject("https://pebback.herokuapp.com/peb/resumes/1",
			// Resume.class);
			System.out.println("Retour avant vaut:= ");
			// String retour = restTemplate.getForObject("http://localhost:9060/wakeup/",
			// String.class);
			String retour = restTemplate.getForObject("https://pebback.herokuapp.com/wakeup/", String.class);
			System.out.println("Retour vaut:= " + retour);

		};
	}

	@Scheduled(initialDelay = 2000L, fixedDelayString = "${someJob.delay}")
	// toutesles 2 secondes
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
