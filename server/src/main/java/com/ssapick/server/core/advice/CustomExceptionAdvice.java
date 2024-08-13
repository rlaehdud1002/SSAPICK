package com.ssapick.server.core.advice;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.core.response.CustomValidationError;
import com.ssapick.server.core.response.ErrorResponse;
import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public ErrorResponse exception(Exception e) {
        if (!e.getMessage().contains("No static resource")) {
            Sentry.captureException(e);
            log.error("message: {}", e.getMessage());
        }
        return ErrorResponse.of(ErrorCode.SERVER_ERROR);
    }

    @ExceptionHandler({AuthorizationDeniedException.class})
    public ResponseEntity<ErrorResponse> authenticateException(Exception e) {
        Sentry.captureException(e);
        log.error("access exception: {}", e.getMessage());
        ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode, e.getMessage()));
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> baseException(BaseException e) {
        Sentry.captureException(e);
        log.error("message: {}", e.getMessage());
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode, e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomValidationError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Sentry.captureException(e);
        CustomValidationError customValidationError = new CustomValidationError(e.getBindingResult());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(customValidationError);
    }
}
