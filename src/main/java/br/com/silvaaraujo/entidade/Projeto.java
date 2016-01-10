package br.com.silvaaraujo.entidade;

import java.io.Serializable;

import org.bson.types.ObjectId;

public class Projeto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ObjectId id;
	private String nome;
	private String contextoWeb;
	private String repositorioGit;
	private String nomeImagemDocker;
	private String portas;
	private String comandoDocker;
	private String comandoScript;

	public Projeto() {
		super();
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRepositorioGit() {
		return repositorioGit;
	}

	public void setRepositorioGit(String repositorioGit) {
		this.repositorioGit = repositorioGit;
	}

	public String getNomeImagemDocker() {
		return nomeImagemDocker;
	}

	public void setNomeImagemDocker(String nomeImagemDocker) {
		this.nomeImagemDocker = nomeImagemDocker;
	}
	
	public String getComandoDocker() {
		return comandoDocker;
	}

	public void setComandoDocker(String comandoDocker) {
		this.comandoDocker = comandoDocker;
	}
	
	public String getPortas() {
		return portas;
	}

	public void setPortas(String portas) {
		this.portas = portas;
	}

	public String getComandoScript() {
		return comandoScript;
	}

	public void setComandoScript(String comandoScript) {
		this.comandoScript = comandoScript;
	}

	public String getContextoWeb() {
		return contextoWeb;
	}

	public void setContextoWeb(String contextoWeb) {
		this.contextoWeb = contextoWeb;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Projeto other = (Projeto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}