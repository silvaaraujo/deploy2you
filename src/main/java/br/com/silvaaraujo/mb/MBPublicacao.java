package br.com.silvaaraujo.mb;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.silvaaraujo.dao.PublicacaoDAO;
import br.com.silvaaraujo.entidade.Publicacao;

@ViewScoped
@Named("mbPublicacao")
public class MBPublicacao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Publicacao publicacao;
	
	@PostConstruct
	public void init() {
		publicacao = new Publicacao();
	}

	public void publicar() {
		this.publicacao.setAtivo(Boolean.TRUE);
		this.publicacao.setData(new Date());
		this.publicacao.setProjeto("Deploy2You");
		this.publicacao.setUrl("localhost:8080/deploy2you");
		this.publicacao.setUser("admin");
		new PublicacaoDAO().insert(this.publicacao);
		this.limpar();
	}

	private void limpar() {
		this.publicacao = new Publicacao();
	}

	public Publicacao getPublicacao() {
		return publicacao;
	}
	
}