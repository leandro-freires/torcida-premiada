package br.com.apptrechos.torcidapremiada.model.validation.group;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import br.com.apptrechos.torcidapremiada.model.Contribuinte;

public class ContribuinteGroupSequenceProvider implements DefaultGroupSequenceProvider<Contribuinte> {

	@Override
	public List<Class<?>> getValidationGroups(Contribuinte contribuinte) {
		List<Class<?>> grupos = new ArrayList<>();
		grupos.add(Contribuinte.class);
		
		if (isPessoaSelecionada(contribuinte)) {
			grupos.add(contribuinte.getTipoPessoa().getGrupo());
		}
		
		return grupos;
	}

	private boolean isPessoaSelecionada(Contribuinte contribuinte) {
		return contribuinte != null && contribuinte.getTipoPessoa() != null;
	}
	
}
