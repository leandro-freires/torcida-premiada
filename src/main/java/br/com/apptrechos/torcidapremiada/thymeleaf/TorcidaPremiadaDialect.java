package br.com.apptrechos.torcidapremiada.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import br.com.apptrechos.torcidapremiada.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import br.com.apptrechos.torcidapremiada.thymeleaf.processor.MessageElementTagProcessor;
import br.com.apptrechos.torcidapremiada.thymeleaf.processor.OrderElementTagProcessor;
import br.com.apptrechos.torcidapremiada.thymeleaf.processor.PaginationElementTagProcessor;

public class TorcidaPremiadaDialect extends AbstractProcessorDialect {
	public TorcidaPremiadaDialect() {
		super("Torcida Premiada Dialect", "tp", StandardDialect.PROCESSOR_PRECEDENCE);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processadores = new HashSet<>();
		processadores.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
		processadores.add(new MessageElementTagProcessor(dialectPrefix));
		processadores.add(new OrderElementTagProcessor(dialectPrefix));
		processadores.add(new PaginationElementTagProcessor(dialectPrefix));
		
		return processadores;
	}
}
