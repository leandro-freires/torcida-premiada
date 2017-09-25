package br.com.apptrechos.torcidapremiada.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.constraints.Pattern;
import javax.validation.Payload;

import br.com.apptrechos.torcidapremiada.validation.validators.AtributoDeConfirmacaoValidator;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { AtributoDeConfirmacaoValidator.class })
public @interface AtributoDeConfirmacao {
	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "Atributos são diferentes";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	String atributo();
	String atributoDeConfirmacao();
 }
