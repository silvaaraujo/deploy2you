package br.com.silvaaraujo.utils;

import java.io.IOException;

import javax.enterprise.inject.Model;

import br.com.silvaaraujo.entidade.Projeto;
import br.com.silvaaraujo.entidade.Publicacao;

@Model
public class DockerUtils {

	/**
	 * Metodo responsavel por criar, iniciar e publicar a tag informada na publicacao
	 * @param publicacao
	 * @param projeto
	 */
	public void createContainer(Publicacao publicacao, Projeto projeto) {
		try {
			//this.runContainer(projeto, publicacao);
			//executeScript(projeto, publicacao);
			
			//criando o commando de criacao do container
			String dockerCommand = this.createCommand(projeto, publicacao);
			System.out.println(dockerCommand);
			
			//executando o comando para criar o container
			//new LocalShellUtils().executarComando(reportNameContainer(dockerCommand, publicacao.getContainer()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String createCommand(Projeto projeto, Publicacao publicacao) {
		//trocando o nome da variavel [containerName] pelo nome do container setado dinamicamente na publicacao
		String dockerCommand = projeto.getComandoDocker().replace("[containerName]", publicacao.getContainer());
		
		//realizando o binding das portas
		String[] containerPorts = projeto.getPortas().split(",");
		for(int i=0; i<containerPorts.length; i++) {
			dockerCommand = dockerCommand.replace("["+i+"]", String.valueOf(publicacao.getUsedPorts().get(i)));
		}
		return dockerCommand;
	}
	
	private void executeScript(Projeto projeto, Publicacao publicacao) {
		PublicacaoUtils publicador = new PublicacaoUtils();
		publicador.setProjeto(projeto);
		publicador.setPublicacao(publicacao);
		Thread thread = new Thread(publicador);
		thread.start();
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