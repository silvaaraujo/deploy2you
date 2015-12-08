package br.com.silvaaraujo.utils;

import java.io.IOException;

import javax.enterprise.inject.Model;

import br.com.silvaaraujo.entidade.Projeto;
import br.com.silvaaraujo.entidade.Publicacao;

@Model
public class DockerUtils {
	
	public void createContainer(int totalContainer, Publicacao publicacao, Projeto projeto) {
		try {
			runContainer(projeto, publicacao.getContainer());
			executeScript(projeto, publicacao);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void executeScript(Projeto projeto, Publicacao publicacao) {
		PublicacaoUtils publicador = new PublicacaoUtils();
		publicador.setProjeto(projeto);
		publicador.setPublicacao(publicacao);
		Thread thread = new Thread(publicador);
		thread.start();
	}

	private void runContainer(Projeto projeto, String nameContainer) throws IOException {
		LocalShellUtils bash = new LocalShellUtils();
		String dockerCommand = bindPortas(projeto);
		bash.executarComando(reportNameContainer(dockerCommand, nameContainer));
	}

	private String bindPortas(Projeto projeto) {
		String[] portas = projeto.getPortas().split(",");
		String dockerCommand = projeto.getComandoDocker();
		
		for(int i=0; i<portas.length; i++) {
			dockerCommand = dockerCommand.replace("["+i+"]", String.valueOf(portas[i]));
		}
		return dockerCommand;
	}
	
	private String reportNameContainer(String dockerCommand, String nameContainer) {
		return dockerCommand.replace("[nameContainer]", nameContainer);
	}
	
	public void starContainer(String nameContainer) {
		LocalShellUtils bash = new LocalShellUtils();
		try {
			bash.executarComando("docker start " + nameContainer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stopContainer(String nameContainer) {
		LocalShellUtils bash = new LocalShellUtils();
		try {
			bash.executarComando("docker stop " + nameContainer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeContainer(String nameContainer) {
		LocalShellUtils bash = new LocalShellUtils();
		try {
			bash.executarComando("docker rm " + nameContainer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}