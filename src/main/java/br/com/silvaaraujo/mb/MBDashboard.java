package br.com.silvaaraujo.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

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
			initPublicacoes();
		}
		return publicacoes;
	}

	public void setPublicacoes(List<Publicacao> publicacoes) {
		this.publicacoes = publicacoes;
	}

	private void initPublicacoes() {
		Publicacao p = null;
		for (int i = 1; i <= 10; i++) {
			p = new Publicacao();
			p.setId(Long.valueOf(i));
			p.setData(new Date());
			p.setTag("1.0.0 - " + i);
			p.setUrl("http://localhost:8080");
			p.setUser("Usuario - " + i);
			this.publicacoes.add(p);
			p = null;
		}
	}
	
	public void remover(Publicacao o) {
		System.out.println("removendo tag " + o.getTag());
	}
}