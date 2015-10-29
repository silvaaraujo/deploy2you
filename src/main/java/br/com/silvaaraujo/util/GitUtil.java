package br.com.silvaaraujo.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GitUtil {
	
	public List<String> listarTagsRepositorio(String repositorio, String app, 
			String usuario, String senha) {
		
		String respComando = "";
		String tagsResp[];
		List<String> listaTags = new ArrayList<>();
		LocalShellUtil shell = new LocalShellUtil();
		
		try {
			shell.executarComando("cd ~; mkdir repositorios;cd repositorios;git clone " + repositorio);
			shell.executarComando("cd ~/repositorios/" + app + ";git pull");
			respComando = shell.executarComando("cd ~/repositorios/" + app +";git fetch --all;git tag");
			shell.executarComando("cd ~;rm -R repositorios");
			
			tagsResp = respComando.split(" ");
			
			for (String tag : tagsResp) {
				listaTags.add(tag);
			}
			
		} catch (IOException e) {
			System.out.println("Erro ao listar tags do repositório");
			e.printStackTrace();
		}
		
		return listaTags;
	}
}
