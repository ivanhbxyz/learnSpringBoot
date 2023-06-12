package com.ivanhb.contractorman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) 
public class ContractormanApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContractormanApplication.class, args);
	}

}

/* 
	Source: https://www.baeldung.com/spring-boot-react-crud
*/