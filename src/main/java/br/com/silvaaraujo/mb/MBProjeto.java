package br.com.silvaaraujo.mb;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.silvaaraujo.dao.ProjetoDAO;
import br.com.silvaaraujo.entidade.Projeto;
import br.com.silvaaraujo.utils.FileUtils;
import br.com.silvaaraujo.utils.GitUtils;

@RequestScoped
@Named("mbProjeto")
public class MBProjeto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProjetoDAO projetoDAO;
	
	@Inject
	private FileUtils fileUtils;
	
	@Inject
	private GitUtils gitUtils;
	
	private Projeto projeto;
	private List<Projeto> projetos = null;
	
	@PostConstruct
	public void init() {
		this.projetos = new ArrayList<>();
		this.projeto = new Projeto();
	}
	
	public void gravar() {
		this.projetoDAO.insert(this.projeto);
		cloneRepository();
		this.limpar();
	}
	
	public void cloneRepository() {
		try {
			if (fileUtils.getExisteDiretorio(this.projeto.getDiretorio())) {
				fileUtils.deleteDir(this.projeto.getDiretorio());
			}
			
			gitUtils.cloneRepository(this.projeto.getRepositorioGit(), this.getProjeto().getDiretorio());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void limpar() {
		this.projeto = new Projeto();
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void remover(Projeto o) {
		System.out.println("Removendo o projeto " + o.getNome());
		this.projetoDAO.removeById(o.getId());
		
		this.projetos = new ArrayList<>();
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
}