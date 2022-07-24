package br.com.gft.validation.constraintvalidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.gft.validation.CRMV;

public class CrmvValidator implements ConstraintValidator<CRMV, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value.length() == 5 && !value.isBlank() && !value.isEmpty() && !value.contains(" ")
				&& value.chars().allMatch(x -> Character.isDigit(x));
	}

	@Override
	public void initialize(CRMV constraintAnnotation) {
		constraintAnnotation.message();
	}

}
