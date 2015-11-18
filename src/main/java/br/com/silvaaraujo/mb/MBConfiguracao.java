package br.com.silvaaraujo.mb;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.silvaaraujo.dao.ConfiguracaoDAO;
import br.com.silvaaraujo.entidade.Configuracao;

@ViewScoped
@Named("mbConfiguracao")
public class MBConfiguracao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ConfiguracaoDAO configuracaoDAO;
	
	private Configuracao configuracao;
	
	@PostConstruct
	public void init() {
		this.configuracao = this.getConfiguracao();
	}
	
	public void gravar() {
		if (!validar()) {
			return;
		}
		this.configuracaoDAO.insert(this.configuracao);
	}

	public Configuracao getConfiguracao() {
		if (this.configuracao == null) {
			this.configuracao = this.configuracaoDAO.buscarConfiguracao();
		}
		
		if (configuracao == null) {
			this.configuracao = new Configuracao();
		}
		
		return this.configuracao;
	}
	
	public void limpar() {
		this.getConfiguracao();
	}

	private boolean validar() {
		if (this.configuracao == null) {
			return false;
		}
		
		if (this.configuracao.getDiretorioBase() == null || "".equals(this.configuracao.getDiretorioBase().trim())) {
			return false;
		}
		
		if (this.configuracao.getUsuarioGit() == null || "".equals(this.configuracao.getUsuarioGit().trim())) {
			return false;
		}
		
		return true;
	}
}