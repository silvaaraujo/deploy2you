package br.com.silvaaraujo.mb;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

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
		RequestContext ctx = RequestContext.getCurrentInstance();
		
		if (!validar(ctx)) {
			return;
		}
		
		this.configuracaoDAO.insert(this.configuracao);
		this.limpar();
		
		ctx.execute("alerta.sucesso('Configuração gravada com sucesso!');");
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
		this.configuracao = null;
		this.getConfiguracao();
	}

	private boolean validar(RequestContext ctx) {
		Boolean valido = Boolean.TRUE;
		
		if (this.configuracao == null) {
			ctx.execute("alerta.erro('Configuração não encontrada, favor informar a administração do sistema.');");
			valido = Boolean.FALSE;
		}
		
		if (this.configuracao.getDiretorioBase() == null || "".equals(this.configuracao.getDiretorioBase().trim())) {
			ctx.execute("alerta.erro('O diretório base é obrigatório.');");
			valido = Boolean.FALSE;
		}
		
		if (this.configuracao.getUsuarioGit() == null || "".equals(this.configuracao.getUsuarioGit().trim())) {
			ctx.execute("alerta.erro('O usuário git é obrigatório.');");
			valido = Boolean.FALSE;
		}
		
		if (this.configuracao.getPasswordUsuarioGit() == null || "".equals(this.configuracao.getPasswordUsuarioGit().trim())) {
			ctx.execute("alerta.erro('O password do usuário git é obrigatório.');");
			valido = Boolean.FALSE;
		}
		
		if (this.configuracao.getNomeHost() == null || this.configuracao.getNomeHost().trim().isEmpty()) {
			ctx.execute("alerta.erro('O nome do host é obrigatório.');");
			valido = Boolean.FALSE;
		}
		
		return valido;
	}
}