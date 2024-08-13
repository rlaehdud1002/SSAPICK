package com.ssapick.server.core.configuration;

import io.sentry.CustomSamplingContext;
import io.sentry.SamplingContext;
import io.sentry.SentryOptions;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
class CustomTracesSamplerCallback implements SentryOptions.TracesSamplerCallback {
    @Override
    public Double sample(SamplingContext context) {
        CustomSamplingContext customSamplingContext = context.getCustomSamplingContext();
        if (customSamplingContext != null) {
            HttpServletRequest request = (HttpServletRequest) customSamplingContext.get("request");
            return 0.1;
        } else {
            return 0.1;
        }
    }
}