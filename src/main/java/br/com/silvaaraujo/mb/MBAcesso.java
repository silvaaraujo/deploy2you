package br.com.silvaaraujo.mb;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named("mbAcesso")
public class MBAcesso implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String user;
	private String password;
	
	public String logar() {
		if (user == null && password == null) {
			return "logar";
		}
		
		if (user.equals("admin") && password.equals("123")) {
			return "index";
		}
		
		return "logar";
	}
	
	public String encerrarSessao() throws IOException{
		return "logar";
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}