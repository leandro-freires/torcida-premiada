package br.com.apptrechos.torcidapremiada.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class MessageElementTagProcessor extends AbstractElementTagProcessor {

	private static final String NOME_TAG = "message";
	private static final int PRECEDENCIA = 1000;

	public MessageElementTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, NOME_TAG, true, null, false, PRECEDENCIA);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
			IElementTagStructureHandler structureHandler) {
		IModelFactory modelFactory = context.getModelFactory();
		IModel createModel = modelFactory.createModel();
		
		createModel.add(modelFactory.createStandaloneElementTag("th:block", "th:replace", "fragments/mensagem-erro-validacao :: error-message"));
		createModel.add(modelFactory.createStandaloneElementTag("th:block", "th:replace", "fragments/mensagem-sucesso :: success-message"));
		
		structureHandler.replaceWith(createModel, true);
	}

}
