package br.com.silvaaraujo.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.bson.types.ObjectId;
import org.primefaces.context.RequestContext;

import br.com.silvaaraujo.dao.ProjetoDAO;
import br.com.silvaaraujo.entidade.Projeto;

@ViewScoped
@Named("mbProjeto")
public class MBProjeto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProjetoDAO projetoDAO;
	
	private Projeto projeto;
	private List<Projeto> projetos = null;

	private String projetoId;
	
	@PostConstruct
	public void init() {
		this.projetos = new ArrayList<>();
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
		this.projetos = new ArrayList<>();		
	}

	public Projeto getProjeto() {
		return projeto;
	}
	
	public void setProjetoId() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String x = String.valueOf(params.get("id"));
		this.projetoId = x;
	}

	public void remover() {
		if (this.projetoId == null) {
			return;
		}

		this.projetoDAO.removeById(new ObjectId(this.projetoId));
		this.limpar();
		pesquisarProjetos();
	}

	private void pesquisarProjetos() {
		this.projetos.addAll(this.projetoDAO.findAll());
	}
	
	public List<Projeto> getProjetos() {
		if (this.projetos == null || this.projetos.isEmpty()) {
			this.pesquisarProjetos();
		}
		return projetos;
	}
	
	public String novo() {
		this.limpar();
		return "cadastrarprojeto";
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
}