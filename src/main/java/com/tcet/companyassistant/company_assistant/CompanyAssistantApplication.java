package com.tcet.companyassistant.company_assistant;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.tcet.companyassistant.repository")
public class CompanyAssistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanyAssistantApplication.class, args);
	}

}
