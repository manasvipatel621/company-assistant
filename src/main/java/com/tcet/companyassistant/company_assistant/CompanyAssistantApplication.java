package com.tcet.companyassistant.company_assistant;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.tcet.companyassistant.repository")
public class CompanyAssistantApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CompanyAssistantApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(CompanyAssistantApplication.class, args);
	}

}
