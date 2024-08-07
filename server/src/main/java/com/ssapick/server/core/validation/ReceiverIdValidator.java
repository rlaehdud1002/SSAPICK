package com.ssapick.server.core.validation;

import com.ssapick.server.core.annotation.ValidPickRequest;
import com.ssapick.server.domain.pick.dto.PickData;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ReceiverIdValidator implements ConstraintValidator<ValidPickRequest, PickData.Create> {

	@Override
	public void initialize(ValidPickRequest constraintAnnotation) {
	}

	@Override
	public boolean isValid(PickData.Create create, ConstraintValidatorContext context) {
		if (create.getStatus() == PickData.PickStatus.PICKED && create.getReceiverId() == null) {
			return false;
		}
		return true;
	}
}