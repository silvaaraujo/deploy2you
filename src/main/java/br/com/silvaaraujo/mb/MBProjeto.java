package br.com.silvaaraujo.mb;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.silvaaraujo.entidade.Projeto;

@ViewScoped
@Named("mbProjeto")
public class MBProjeto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Projeto projeto;
	
	@PostConstruct
	public void init() {
		projeto = new Projeto();
	}
	
	public void cadastrar() {
		System.out.println(projeto.getNome());
		System.out.println(projeto.getRepositorioGit());
		System.out.println(projeto.getNomeImagemDocker());
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
}
