package br.com.silvaaraujo.utils;

import java.io.IOException;

import javax.enterprise.inject.Model;

import br.com.silvaaraujo.entidade.Projeto;

@Model
public class DockerUtils {
	
	public void createContainer(int totalContainer, String nameContainer, Projeto projeto) {
		LocalShellUtils bash = new LocalShellUtils();
		try {
			//docker run --name glassfish -d -p [0]:4848 -p [1]:8080 --link db:mongo glassfish/mongo
			String[] portas = projeto.getPortas().split(",");
			String dockerCommand = projeto.getComandoDocker();
			
			for(int i=0; i<portas.length; i++) {
				dockerCommand = dockerCommand.replace("["+i+"]", String.valueOf(portas[i]));
			}
			
			bash.executarComando(dockerCommand);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}