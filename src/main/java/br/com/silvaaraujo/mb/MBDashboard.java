package br.com.silvaaraujo.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.silvaaraujo.dao.PublicacaoDAO;
import br.com.silvaaraujo.entidade.Publicacao;

@ViewScoped
@Named("mbDashboard")
public class MBDashboard implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Publicacao> publicacoes;
	
	@PostConstruct
	public void init() {
		this.publicacoes = new ArrayList<>();
	}

	public List<Publicacao> getPublicacoes() {
		if (publicacoes.isEmpty()) {
			PublicacaoDAO publicacaoDao = new PublicacaoDAO();
			this.publicacoes.addAll(publicacaoDao.findAll());
		}
		return publicacoes;
	}

	public void setPublicacoes(List<Publicacao> publicacoes) {
		this.publicacoes = publicacoes;
	}

	public void remover(Publicacao o) {
		System.out.println("removendo tag " + o.getTag());
	}
}