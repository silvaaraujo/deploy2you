package br.com.silvaaraujo.utils;

import java.io.IOException;
import java.text.MessageFormat;

import javax.enterprise.inject.Model;

import br.com.silvaaraujo.entidade.Projeto;
import br.com.silvaaraujo.utils.LocalShellUtils;

@Model
public class DockerUtils {
	
	public void createContainer(int totalContainer, String nameContainer, Projeto projeto) {
		LocalShellUtils bash = new LocalShellUtils();
		//docker run --name {0} -d -p {1}:4848 -p {2}:8080 -p {3}:8081 glassfish
		try {
			bash.executarComando(
				MessageFormat.format(projeto.getComandoDocker(),
					nameContainer, getPortAdm(totalContainer, projeto), getPortHttp(totalContainer, projeto), getPortHttps(totalContainer, projeto)));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getPortAdm(int totalContainerStart, Projeto projeto) {
		return String.valueOf(projeto.getPortaAdm() + totalContainerStart);
	}
	
	public String getPortHttp(int totalContainerStart, Projeto projeto) {
		return String.valueOf(projeto.getPortaHttp() + totalContainerStart);
	}
	
	public String getPortHttps(int totalContainerStart, Projeto projeto) {
		return String.valueOf(projeto.getPortaHttps() + totalContainerStart);
	}
}
