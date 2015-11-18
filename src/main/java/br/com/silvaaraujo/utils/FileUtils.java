package br.com.silvaaraujo.utils;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import javax.enterprise.inject.Model;

@Model
public class FileUtils {
	
	public Boolean getExisteDiretorio(String diretorio) {
		
		try {
			if (Files.exists(Paths.get(diretorio))) {
				return Boolean.TRUE;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Boolean.FALSE;
	}
	
	public void deleteDir(String path) throws IOException {
		Path directory = Paths.get(path);
		Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}
}
