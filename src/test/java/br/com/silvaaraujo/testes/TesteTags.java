package br.com.silvaaraujo.testes;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.transport.FetchResult;
import org.junit.Assert;
import org.junit.Test;

import br.com.silvaaraujo.utils.GitUtils;

public class TesteTags {

	//@Test
	public void testBuscarRepository() throws GitAPIException {
		try {
			Repository repo = new GitUtils().getRepository("/home/thiago/java/repo/deploy2you/.git"); 
			Assert.assertNotNull(repo);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	//@Test
	public void testListarBranches() throws GitAPIException {
		try {
			Repository repo = new GitUtils().getRepository("/home/thiago/java/repo/deploy2you/.git"); 
			Assert.assertNotNull(repo);
			
			List<Ref> refs = new GitUtils().getBranches(repo, ListMode.REMOTE);
			Assert.assertNotNull(refs);
			Assert.assertTrue(refs.size() > 0);
			
			System.out.println("BRANCHES: ");
			for (Ref ref : refs) {
				System.out.println(ref.getName());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	//@Test
	public void testListarTags() throws GitAPIException {
		try {
			Repository repo = new GitUtils().getRepository("/home/thiago/java/repo/deploy2you/.git"); 
			Assert.assertNotNull(repo);

			List<Ref> refs = new GitUtils().getTags(repo);
			Assert.assertNotNull(refs);
			Assert.assertTrue(refs.size() > 0);
			
			System.out.println("TAGS: ");
			for (Ref ref : refs) {
				System.out.println(ref.getName());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
/*	
	public static void main(String[] args) {
		try {
			Repository repo = new GitUtils().getRepository("/home/thiago/java/repo/deploy2you/.git");
			Git git = new Git(repo);
			PullCommand pull = git.pull();
			PullResult result = pull.call();
			
			System.out.println(result.getFetchedFrom());
			FetchResult fetchResult = result.getFetchResult();
			
			FetchCommand fetch = git.fetch();
			fetch.setRemote("https://github.com/silvaaraujo/deploy2you.git");
			fetch.setRemoveDeletedRefs(true);
			fetch.isRemoveDeletedRefs();
			FetchResult call = fetch.call();
			
			MergeResult mergeResult = result.getMergeResult();
			
			List<Ref> refs = git.tagList().call();
			System.out.println("TAGS: ");
			for (Ref ref : refs) {
				System.out.println(ref.getName());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
/*
	public static void main(String[] args) {
		try {
			Git.cloneRepository()
			.setURI("https://github.com/silvaaraujo/deploy2you.git")
			.setDirectory(new File("/home/thiago/java/repo/deploy2you"))
			.call();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/	
}