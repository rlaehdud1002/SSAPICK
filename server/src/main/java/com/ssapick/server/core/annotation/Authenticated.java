package com.ssapick.server.core.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
//@Secured({"USER", "ADMIN"})
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public @interface Authenticated {
}
