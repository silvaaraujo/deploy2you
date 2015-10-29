package br.com.silvaaraujo.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LocalShellUtil {

	public String executarComando(final String comando) throws IOException {
		
		final ArrayList<String> comandos = new ArrayList<>();
		comandos.add("/bin/bash");
		comandos.add("-c");
		comandos.add(comando);
		
		BufferedReader br = null;
		
		try {
			
			final ProcessBuilder p  = new ProcessBuilder(comandos);
			final Process process = p.start();
			final InputStream is = process.getInputStream();
			final InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			
			StringBuilder result = new StringBuilder();
			
			while(br.readLine() != null){
				result.append(br.readLine() + " ");
			}
			
			return result.toString();
			
		} catch (IOException ioe) {
			System.out.println("Erro ao executar comando " + ioe.getMessage());
		} finally {
			encerrarConexao(br);
		}
		
		return null;
	}
	
	public void encerrarConexao(final Closeable recurso) {
		try {
			if (recurso != null) {
				recurso.close();
			}
		} catch (IOException ioe) {
			System.out.println("Erro: " + ioe.getMessage());
		}
	}
}
