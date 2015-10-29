package br.com.silvaaraujo.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;

public class GitUtils {

	public Repository getRepository(String repository) throws IOException {
		return new RepositoryBuilder()
			.setMustExist(true)
			.setGitDir(new File(repository))
			.build();
	}
	
	public void pull(Repository repository) {
		try (Git git = new Git(repository)) {
			PullCommand pull = git.pull();
			String remote = pull.getRemote();
		}
		
	}
	
	public List<Ref> getTags(Repository repository) throws GitAPIException {
		try (Git git = new Git(repository)) {
			return git.tagList().call();
		}
	}
	
	public List<Ref> getBranches(Repository repository, ListMode mode) throws GitAPIException {
		try (Git git = new Git(repository)) {
			return git.branchList().setListMode(mode).call();
		}
	}
	
	
}