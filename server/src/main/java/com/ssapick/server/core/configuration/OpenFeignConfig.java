package com.ssapick.server.core.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.ssapick.server.**.**")
public class OpenFeignConfig {

}
