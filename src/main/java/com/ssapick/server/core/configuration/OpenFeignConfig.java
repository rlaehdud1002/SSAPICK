package com.ssapick.server.core.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("org.example.openfeigndemo")
public class OpenFeignConfig {
}
