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
	 * @throws Exception 
	 */
	public void createContainer(Publicacao publicacao, Projeto projeto) throws Exception {
		try {
			//criando o commando de criacao do container
			String dockerCommand = this.createCommand(projeto, publicacao);
			//System.out.println(dockerCommand);
			
			//criando e iniciando o container
			new LocalShellUtils().executarComando(dockerCommand);
			
			//executando o comando para aplicar o script de publicacao
			//TODO
		} catch (Exception e) {
			throw e;
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

	public void startContainer(String nameContainer) throws IOException {
		LocalShellUtils bash = new LocalShellUtils();
		try {
			bash.executarComando("docker start " + nameContainer);
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw e;
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
	
	public void killContainer(String nameContainer) {
		LocalShellUtils bash = new LocalShellUtils();
		try {
			bash.executarComando("docker kill " + nameContainer);
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