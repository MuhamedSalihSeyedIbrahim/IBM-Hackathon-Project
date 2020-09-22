package com.cognizant.orderservice.Config;


import com.cloudant.client.api.ClientBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class cloudantConfig {

    @Value("${cloudant.account}")
    private String account;

    @Value("${cloudant.username}")
    private String username;

    @Value("${cloudant.password}")
    private String password;

    @Bean
    public ClientBuilder Clientbuilder() {
        ClientBuilder builder = ClientBuilder
            .account(this.account)
            .username(this.username)
            .password(this.password);
        return builder;
    }
}
