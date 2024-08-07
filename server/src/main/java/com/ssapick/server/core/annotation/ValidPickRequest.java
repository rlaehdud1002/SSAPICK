package com.ssapick.server.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ssapick.server.core.validation.ReceiverIdValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = ReceiverIdValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPickRequest {
	String message() default "픽을 할 때 상대방이 Null일 수는 없습니다.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}