package com.ssapick.server.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
//@Secured({"ROLE_ADMIN"})
public @interface IsAdmin {
}
