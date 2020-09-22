package com.cognizant.authenticationservice.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;


public class CloudantConfig {

    @Autowired
	private CloudantConfigProperties config;

	@Bean
	public CloudantClient client() {
		ClientBuilder builder = ClientBuilder
			.url(config.getUrl())
			.username(config.getUsername())
			.password(config.getPassword());
		return builder.build();
	}

}