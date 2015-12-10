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
	
	public String editar() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String x = String.valueOf(params.get("id"));
		
		if (x == null || "".equals(x)) {
			return "projetos";
		}
		
		String param ="?faces-redirect=true&amp;projId="+x;
		
		return "cadastroprojeto.xhtml"+param;
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
			this.projetos = new ArrayList<>();
			this.pesquisarProjetos();
		}
		return projetos;
	}
	
	public String novo() {
		this.limpar();
		return "cadastrarprojeto";
	}
	
	public String getProjetoId() {
		return projetoId;
	}

	public void setProjetoId(String projetoId) {
		this.projetoId = projetoId;
	}
}