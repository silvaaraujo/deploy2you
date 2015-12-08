package br.com.silvaaraujo.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.bson.types.ObjectId;

import br.com.silvaaraujo.dao.PublicacaoDAO;
import br.com.silvaaraujo.entidade.Publicacao;
import br.com.silvaaraujo.utils.DockerUtils;

@ViewScoped
@Named("mbDashboard")
public class MBDashboard implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PublicacaoDAO publicacaoDAO;
	
	@Inject
	private DockerUtils docker;
	
	private List<Publicacao> publicacoes;
	private String publicacaoId;
	
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

	public void setPublicacaoId() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String x = String.valueOf(params.get("id"));
		this.publicacaoId = x;
	}
	
	public void remover() {
		if(this.publicacaoId == null) {
			//nunca deve acontecer
			return;
		}
		
		Publicacao publicacao = this.publicacaoDAO.findById(this.publicacaoId);
		
		this.docker.stopContainer(publicacao.getContainer());
		this.docker.removeContainer(publicacao.getContainer());
		this.publicacaoDAO.removeById(new ObjectId(this.publicacaoId));
		limpar();
	}
	
	public void startContainer() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String publicacaoId = String.valueOf(params.get("id"));
		Publicacao publicacao = this.publicacaoDAO.findById(publicacaoId);
		this.docker.starContainer(publicacao.getContainer());
	}
	
	public void stopContainer() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String publicacaoId = String.valueOf(params.get("id"));
		Publicacao publicacao = this.publicacaoDAO.findById(publicacaoId);
		this.docker.stopContainer(publicacao.getContainer());
	}
	
	public void limpar() {
		this.publicacaoId = null;
		this.publicacoes = new ArrayList<>();
	}
}