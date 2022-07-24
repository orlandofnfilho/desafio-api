package br.com.gft.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.gft.validation.constraintvalidation.CrmvValidator;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Constraint(validatedBy = CrmvValidator.class)
public @interface CRMV {

	String message() default "CRMV Inválido";
	Class<?>[] groups() default{};
	Class<? extends Payload>[] payload() default{};

}
