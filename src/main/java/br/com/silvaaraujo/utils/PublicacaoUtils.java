package br.com.silvaaraujo.utils;

import java.io.IOException;

import br.com.silvaaraujo.entidade.Projeto;
import br.com.silvaaraujo.entidade.Publicacao;

public class PublicacaoUtils implements Runnable {
	
	private Publicacao publicacao;
	private Projeto projeto;

	@Override
	public void run() {
		LocalShellUtils bash = new LocalShellUtils();
		try {
			bash.executarComando(commandScriptPublicacao());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String commandScriptPublicacao() {
		return projeto.getComandoScript().replace("[nameContainer]", publicacao.getContainer())
			.replace("[project]", projeto.getNome().toLowerCase())
				.replace("[tag]", publicacao.getTag());
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public Publicacao getPublicacao() {
		return publicacao;
	}

	public void setPublicacao(Publicacao publicacao) {
		this.publicacao = publicacao;
	}
}
