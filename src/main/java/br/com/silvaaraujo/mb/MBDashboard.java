package br.com.silvaaraujo.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.silvaaraujo.dao.PublicacaoDAO;
import br.com.silvaaraujo.entidade.Publicacao;

@RequestScoped
@Named("mbDashboard")
public class MBDashboard implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PublicacaoDAO publicacaoDAO;
	
	private List<Publicacao> publicacoes;
	
	@PostConstruct
	public void init() {
		this.publicacoes = new ArrayList<>();
	}

	public List<Publicacao> getPublicacoes() {
		if (publicacoes.isEmpty()) {
			pesquisarPublicacoes();
		}
		return publicacoes;
	}

	private void pesquisarPublicacoes() {
		this.publicacoes.addAll(publicacaoDAO.findAll());
	}

	public void setPublicacoes(List<Publicacao> publicacoes) {
		this.publicacoes = publicacoes;
	}

	public void remover(Publicacao o) {
		System.out.println("removendo tag " + o.getTag());
		this.publicacaoDAO.removeById(o.getId());
		
		this.publicacoes = new ArrayList<>();
		pesquisarPublicacoes();
	}
}