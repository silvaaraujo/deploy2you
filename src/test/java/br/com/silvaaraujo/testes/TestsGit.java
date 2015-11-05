package br.com.silvaaraujo.testes;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.junit.Assert;
import org.junit.Test;

import br.com.silvaaraujo.utils.GitUtils;

public class TestsGit {

	//@Test
	public void createRepositoryTest() throws GitAPIException {
		try  {
			if (Files.exists(Paths.get("/home/thiago/temp/deploy2you"))){
				deleteDir("/home/thiago/temp/deploy2you");
			}
			new GitUtils().cloneRepository("https://github.com/silvaaraujo/deploy2you.git", "/home/thiago/temp/deploy2you");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	private void deleteDir(String path) throws IOException {
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

	//@Test
	public void getRepositoryTest() throws GitAPIException {
		try (Repository repo = new GitUtils().getRepository("/home/thiago/temp/deploy2you/.git")) { 
			Assert.assertNotNull(repo);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	//@Test
	public void getBranchesTest() throws GitAPIException {
		try { 
			String repo = "/home/thiago/temp/deploy2you/.git";
			List<String> refs = new GitUtils().getBranches(repo);
			Assert.assertNotNull(refs);
			Assert.assertTrue(refs.size() > 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	//@Test
	public void testListarTags() throws GitAPIException {
		try { 
			String repo = "/home/thiago/temp/deploy2you/.git";
			List<String> refs = new GitUtils().getTags(repo);
			Assert.assertNotNull(refs);
			Assert.assertTrue(refs.size() > 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
}