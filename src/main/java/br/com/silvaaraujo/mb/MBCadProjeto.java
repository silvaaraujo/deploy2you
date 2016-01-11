package br.com.silvaaraujo.mb;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.silvaaraujo.dao.ProjetoDAO;
import br.com.silvaaraujo.entidade.Projeto;

@ViewScoped
@Named("mbCadProjeto")
public class MBCadProjeto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProjetoDAO projetoDAO;
	
	private Projeto projeto;

	private String projetoId;
	
	@PostConstruct
	public void init() {
		this.projeto = new Projeto();
	}
	
	public void gravar() {
		RequestContext ctx = RequestContext.getCurrentInstance();
		
		if (!validar(ctx)) {
			return;
		}
		
		this.projetoDAO.insert(this.projeto);
		this.limpar();
		
		ctx.execute("alerta.sucesso('Projeto gravado com sucesso!');");
	}
	
	public void limpar() {
		this.projetoId = null;
		this.projeto = new Projeto();
	}

	public Projeto getProjeto() {
		return projeto;
	}

	private boolean validar(RequestContext ctx) {
		Boolean valido = Boolean.TRUE;
		
		if (this.projeto == null) {
			ctx.execute("alerta.erro('Projeto nulo, favor informar a administração do sistema.');");
			valido = Boolean.FALSE;
		}
		
		if (this.projeto.getNome() == null || "".equals(this.projeto.getNome().trim())) {
			ctx.execute("alerta.erro('O nome do projeto é obrigatório.');");
			valido = Boolean.FALSE;
		}
		
		if (this.projeto.getContextoWeb() == null || "".equals(this.projeto.getContextoWeb().trim())) {
			ctx.execute("alerta.erro('O contexto web do projeto é obrigatório.');");
			valido = Boolean.FALSE;
		}
		
		if (this.projeto.getNomeBaseContainer() == null || "".equals(this.projeto.getNomeBaseContainer().trim())) {
			ctx.execute("alerta.erro('O nome base do container é obrigatório.');");
			valido = Boolean.FALSE;
		} else {
			Pattern p = Pattern.compile("[^A-Za-z0-9_.-]");
			Matcher matcher = p.matcher(this.projeto.getNomeBaseContainer());
			if (matcher.find()) {
				ctx.execute("alerta.erro('O nome base do container não pode conter caracteres especiais.');");
				valido = Boolean.FALSE;
			}
		}

		if (this.projeto.getRepositorioGit() == null || "".equals(this.projeto.getRepositorioGit().trim())) {
			ctx.execute("alerta.erro('O repositório git é obrigatório.');");
			valido = Boolean.FALSE;
		}
		
		if (this.projeto.getNomeImagemDocker() == null || "".equals(this.projeto.getNomeImagemDocker().trim())) {
			ctx.execute("alerta.erro('A imagem docker é obrigatória.');");
			valido = Boolean.FALSE;
		}
		
		if (this.projeto.getPortas() == null || "".equals(this.projeto.getPortas())) {
			ctx.execute("alerta.erro('As portas são obrigatórias.');");
			valido = Boolean.FALSE;
		}
		
		if (this.projeto.getComandoDocker() == null || "".equals(this.projeto.getComandoDocker().trim())) {
			ctx.execute("alerta.erro('O comando docker é obrigatório.');");
			valido = Boolean.FALSE;
		}
		
		if (this.projeto.getComandoScript() == null || "".equals(this.projeto.getComandoScript().trim())) {
			ctx.execute("alerta.erro('O comando do script de publicação é obrigatório.');");
			valido = Boolean.FALSE;
		}
		
		return valido;
	}

	public String getProjetoId() {
		return projetoId;
	}

	public void setProjetoId(String projetoId) {
		this.projetoId = projetoId;
		this.buscarProjeto();
	}

	private void buscarProjeto() {
		if (this.projetoId != null || !this.projetoId.trim().isEmpty()) {
			this.projeto = this.projetoDAO.findById(this.projetoId);
		}
	}
}