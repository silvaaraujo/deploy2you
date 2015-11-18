package br.com.silvaaraujo.entidade;

import java.io.Serializable;

import org.bson.types.ObjectId;

public class Configuracao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ObjectId id;
	private String diretorioBase;
	private String usuarioGit;
	private String passwordUsuarioGit;

	public Configuracao() {
		super();
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getDiretorioBase() {
		return diretorioBase;
	}

	public void setDiretorioBase(String diretorioBase) {
		this.diretorioBase = diretorioBase;
	}

	public String getUsuarioGit() {
		return usuarioGit;
	}

	public void setUsuarioGit(String usuarioGit) {
		this.usuarioGit = usuarioGit;
	}

	public String getPasswordUsuarioGit() {
		return passwordUsuarioGit;
	}

	public void setPasswordUsuarioGit(String passwordUsuarioGit) {
		this.passwordUsuarioGit = passwordUsuarioGit;
	}
	
}