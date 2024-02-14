package com.paulmount.paulfoliodisplay.config;

import com.paulmount.paulfoliodisplay.services.ProjectClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * *  Created by paulm on 2/6/2024
 */

@Configuration
public class ClientConfig {

    @Bean
    ProjectClientService projectClient() {
        WebClient client = WebClient.builder()
                .baseUrl("http://localhost:8081/api/v1")
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();

        return factory.createClient(ProjectClientService.class);
    }

}
