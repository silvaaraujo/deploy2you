package br.com.silvaaraujo.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.silvaaraujo.dao.ProjetoDAO;
import br.com.silvaaraujo.dao.PublicacaoDAO;
import br.com.silvaaraujo.entidade.Projeto;
import br.com.silvaaraujo.entidade.Publicacao;
import br.com.silvaaraujo.utils.GitUtils;
import br.com.silvaaraujo.utils.Tags;

@RequestScoped
@Named("mbPublicacao")
@Path("/manterPublicacao")
public class MBPublicacao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PublicacaoDAO publicacaoDAO;
	
	@Inject
	private ProjetoDAO projetoDAO;
	
	@Inject
	private GitUtils gitUtils;
	
	private Publicacao publicacao;
	
	private List<Projeto> projetos;
	
	@PostConstruct
	public void init() {
		publicacao = new Publicacao();
		this.projetos = new ArrayList<>();
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

	@GET
	@Path("tags")
	@Produces({ MediaType.APPLICATION_JSON})
	public Tags getTags(@QueryParam("projetoId") String projetoId) {

		if (projetoId == null || projetoId.trim().isEmpty()) {
			return new Tags();
		}
		
		Projeto projeto = this.projetoDAO.findById(projetoId);
		List<String> lista = null;
		
		try {
			lista = this.gitUtils.getTags(projeto.getRepositorioGit());
			if (lista == null || lista.isEmpty()) {
				return new Tags();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Tags();
		}
		
		Tags tags = new Tags();
		for (String s : lista) {
			tags.getTag().add(s);
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