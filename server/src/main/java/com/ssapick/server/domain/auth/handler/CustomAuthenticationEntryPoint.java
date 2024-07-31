package com.ssapick.server.domain.auth.handler;

import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.core.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        System.out.println("CustomAuthenticationEntryPoint.commence");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        if (request.getAttribute("error-message") == null) {
            request.setAttribute("error-message", ErrorCode.INVALID_TOKEN.getMessage());
        }
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_TOKEN, request.getAttribute("error-message").toString());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(errorResponse.toJson());
    }
}