package com.cognizant.authenticationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ibm.cloudant.spring.framework.EnableCloudant;


@SpringBootApplication
@EnableCloudant
public class AuthenticationServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}

}
