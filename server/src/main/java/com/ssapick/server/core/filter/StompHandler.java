package com.ssapick.server.core.filter;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.domain.auth.service.JWTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@Component
public class StompHandler implements ChannelInterceptor {
    private final JWTService jwtService;



    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (accessor.getCommand() == StompCommand.CONNECT) {
            String accessToken = accessor.getFirstNativeHeader("Authorization");
            Authentication authentication = parseAccessToken(accessToken);
            if (authentication == null) {
                throw new BaseException(ErrorCode.INVALID_ACCESS_TOKEN);
            }
            log.debug("authentication: {}", authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            accessor.setUser(authentication);
        }
        return message;
    }

    private Authentication parseAccessToken(String accessToken) {
        if (accessToken != null) {
            String bearerToken = accessToken.trim();
            if (!bearerToken.trim().isEmpty() && bearerToken.startsWith("Bearer ")) {
                accessToken = bearerToken.substring(7);
                try {
                    return jwtService.parseAuthentication(accessToken);
                } catch (Exception e) {
                    log.error("error ", e);
                }
            }
        }
        throw new BaseException(ErrorCode.INVALID_ACCESS_TOKEN);
    }
}
