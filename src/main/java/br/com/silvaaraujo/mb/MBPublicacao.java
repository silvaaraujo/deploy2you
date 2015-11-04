package br.com.silvaaraujo.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.silvaaraujo.dao.ProjetoDAO;
import br.com.silvaaraujo.dao.PublicacaoDAO;
import br.com.silvaaraujo.entidade.Projeto;
import br.com.silvaaraujo.entidade.Publicacao;

@RequestScoped
@Named("mbPublicacao")
public class MBPublicacao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PublicacaoDAO publicacaoDAO;
	
	@Inject
	private ProjetoDAO projetoDAO;
	
	private Publicacao publicacao;
	
	private List<Projeto> projetos;
	private List<String> tags;
	
	@PostConstruct
	public void init() {
		publicacao = new Publicacao();
		this.projetos = new ArrayList<>();
		this.tags = new ArrayList<>();
	}

	public void publicar() {
		this.criarPublicacao();
		this.publicacaoDAO.insert(this.publicacao);
		this.limpar();
	}

	private void criarPublicacao() {
		this.publicacao.setAtivo(Boolean.TRUE);
		this.publicacao.setData(new Date());
		this.publicacao.setProjeto("deploy2You");
		this.publicacao.setUrl("localhost:"+getPorta()+"/deploy2you");
		this.publicacao.setUser("admin");
	}

	private int getPorta() {
		List<Publicacao> list = this.publicacaoDAO.findAll();
		int qtdPublicada = list != null ? list.size() : 0;
		qtdPublicada++;
		int porta = 8080+qtdPublicada;
		return porta; 
	}

	private void limpar() {
		this.publicacao = new Publicacao();
	}

	public Publicacao getPublicacao() {
		return publicacao;
	}

	//TODO
	public List<String> getTags() {
		if (this.tags == null || this.tags.isEmpty()) {
			
		}
		return tags;
	}
	
	public List<Projeto> getProjetos() {
		if (this.projetos == null || this.projetos.isEmpty()) {
			 this.projetos.addAll(this.projetoDAO.findAll());
		}
		return projetos;
	}
}