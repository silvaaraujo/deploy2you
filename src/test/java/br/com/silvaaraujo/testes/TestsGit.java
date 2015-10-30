package br.com.silvaaraujo.testes;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.junit.Assert;
import org.junit.Test;

import br.com.silvaaraujo.utils.GitUtils;

public class TestsGit {

	@Test
	public void createRepositoryTest() throws GitAPIException {
		try  {
			new GitUtils().cloneRepository("https://github.com/silvaaraujo/deploy2you.git", "/home/thiago/temp/deploy2you");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void getRepositoryTest() throws GitAPIException {
		try (Repository repo = new GitUtils().getRepository("/home/thiago/temp/deploy2you/.git")) { 
			Assert.assertNotNull(repo);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void getBranchesTest() throws GitAPIException {
		try (Repository repo = new GitUtils().getRepository("/home/thiago/temp/deploy2you/.git")) { 
			Assert.assertNotNull(repo);
			
			List<String> refs = new GitUtils().getBranches(repo);
			Assert.assertNotNull(refs);
			Assert.assertTrue(refs.size() > 0);
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testListarTags() throws GitAPIException {
		try (Repository repo = new GitUtils().getRepository("/home/thiago/temp/deploy2you/.git")) { 
			Assert.assertNotNull(repo);

			List<String> refs = new GitUtils().getTags(repo);
			Assert.assertNotNull(refs);
			Assert.assertTrue(refs.size() > 0);
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
}