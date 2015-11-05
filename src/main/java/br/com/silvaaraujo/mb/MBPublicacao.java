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
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.com.silvaaraujo.dao.ProjetoDAO;
import br.com.silvaaraujo.dao.PublicacaoDAO;
import br.com.silvaaraujo.entidade.Projeto;
import br.com.silvaaraujo.entidade.Publicacao;
import br.com.silvaaraujo.utils.GitUtils;

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

	@GET
	@Path("tags")
	public Response getTags(@PathParam("projetoId") String projetoId) {
		this.tags = new ArrayList<>();
		if (projetoId == null || projetoId.trim().isEmpty()) {
			return Response.ok().build();
		}
		
		Projeto projeto = this.projetoDAO.findById("563ac57f01bf5d4fb58c0198");
		try {
			this.tags = this.gitUtils.getTags(projeto.getRepositorioGit());
			if (this.tags == null || this.tags.isEmpty()) {
				this.tags = new ArrayList<>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.ok().build();
	}
	
	public List<Projeto> getProjetos() {
		if (this.projetos == null || this.projetos.isEmpty()) {
			 this.projetos.addAll(this.projetoDAO.findAll());
		}
		return projetos;
	}
	
}