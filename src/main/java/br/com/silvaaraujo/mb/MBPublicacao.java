package br.com.silvaaraujo.mb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;

import br.com.silvaaraujo.dao.PublicacaoDAO;
import br.com.silvaaraujo.entidade.Publicacao;

@ViewScoped
@Named("mbPublicacao")
public class MBPublicacao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Publicacao publicacao;
	
	private String localPath;
	private String remotePath;
    private Repository localRepo;
    private Git git;
	
	@PostConstruct
	public void init() {
		publicacao = new Publicacao();
		
	}

	public void publicar() {
		this.criarPublicacao();
		//verificar se existe o repositorio e caso nao existar clonar
		//buscar a tag (git)
		//compilar a tag (mvn)
		//criar o container (docker/runtime)
		//aplicar a tag (script/runtime/jenkins)
		new PublicacaoDAO().insert(this.publicacao);
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
		List<Publicacao> list = new PublicacaoDAO().findAll();
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
	
	public void getTag() {
	
	}
	
}