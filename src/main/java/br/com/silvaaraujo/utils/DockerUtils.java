package br.com.silvaaraujo.utils;

import java.io.IOException;
import java.text.MessageFormat;

import javax.enterprise.inject.Model;

import br.com.silvaaraujo.utils.LocalShellUtils;

@Model
public class DockerUtils {
	
	private int portAdm = 4849;
	private int portHttp = 8081;
	private int portHttps = 8443;
	
	public void createContainer(int totalContainer, String nameContainer) {
		LocalShellUtils bash = new LocalShellUtils();
		try {
			bash.executarComando(
				MessageFormat.format("docker run --name {0} -d -p {1}:4848 -p {2}:8080 -p {3}:8081 glassfish",
					nameContainer, getPortAdm(totalContainer), getPortHttp(totalContainer), getPortHttps(totalContainer)));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getPortAdm(int totalContainerStart) {
		return String.valueOf(this.portAdm + totalContainerStart);
	}
	
	public String getPortHttp(int totalContainerStart) {
		return String.valueOf(this.portHttp + totalContainerStart);
	}
	
	public String getPortHttps(int totalContainerStart) {
		return String.valueOf(this.portHttps + totalContainerStart);
	}
}
