package br.com.apptrechos.torcidapremiada.model;

import br.com.apptrechos.torcidapremiada.repository.Imoveis;

public enum StatusImovel {
	ATIVO {
		@Override
		public void executar(Long[] codigos, Imoveis imoveis) {
			imoveis.findByCodigoIn(codigos).forEach(i -> i.setStatus(true));
		}
	},
	INATIVO {
		@Override
		public void executar(Long[] codigos, Imoveis imoveis) {
			imoveis.findByCodigoIn(codigos).forEach(i -> i.setStatus(false));
		}
	};
	
	public abstract void executar(Long[] codigos, Imoveis imoveis);
}
