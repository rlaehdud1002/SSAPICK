package com.ssapick.server.core.configuration;

import io.sentry.spring.jakarta.EnableSentry;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@EnableSentry(dsn = "https://7e93ac16ca7b136493b81734cc637c86@o4507768254627840.ingest.us.sentry.io/4507768257380352")
@Configuration
public class SentryConfig {

}
