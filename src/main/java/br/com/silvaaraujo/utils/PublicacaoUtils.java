package br.com.silvaaraujo.utils;

import java.io.IOException;

public class PublicacaoUtils implements Runnable {
	
	private String command;

	public PublicacaoUtils(String command) {
		super();
		this.command = command;
	}

	@Override
	public void run() {
		try {
			if (command == null || command.trim().isEmpty()) {
				return;
			}
			new LocalShellUtils().executarComando(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
