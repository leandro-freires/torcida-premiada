package br.com.apptrechos.torcidapremiada.validation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.apache.commons.beanutils.BeanUtils;

import br.com.apptrechos.torcidapremiada.validation.AtributoDeConfirmacao;

public class AtributoDeConfirmacaoValidator implements ConstraintValidator<AtributoDeConfirmacao, Object> {
	private String atributo;
	private String atributoDeConfirmacao;

	@Override
	public void initialize(AtributoDeConfirmacao constraintAnnotation) {
		this.atributo = constraintAnnotation.atributo();
		this.atributoDeConfirmacao = constraintAnnotation.atributoDeConfirmacao();
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		boolean valido = false;
		
		try {
			Object valorDoAtributo = BeanUtils.getProperty(object, this.atributo);
			Object valorDoAtributoDeConfirmacao = BeanUtils.getProperty(object, this.atributoDeConfirmacao);
			
			valido = allIsNull(valorDoAtributo, valorDoAtributoDeConfirmacao) || allIsEquals(valorDoAtributo, valorDoAtributoDeConfirmacao);
		} catch (Exception e) {
			throw new RuntimeException("Erro na recuperação dos valores dos atributos", e);
		}
		
		if (!valido) {
			context.disableDefaultConstraintViolation();
			String mensagem = context.getDefaultConstraintMessageTemplate();
			ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(mensagem);
			violationBuilder.addPropertyNode(this.atributoDeConfirmacao).addConstraintViolation();
		}
		
		return valido;
	}

	private boolean allIsEquals(Object valorDoAtributo, Object valorDoAtributoDeConfirmacao) {
		return valorDoAtributo != null && valorDoAtributo.equals(valorDoAtributoDeConfirmacao);
	}

	private boolean allIsNull(Object valorDoAtributo, Object valorDoAtributoDeConfirmacao) {
		return valorDoAtributo == null && valorDoAtributoDeConfirmacao == null;
	}

}
