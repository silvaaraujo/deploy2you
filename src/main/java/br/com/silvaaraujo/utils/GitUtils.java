package br.com.silvaaraujo.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.enterprise.inject.Model;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;

@Model
public class GitUtils {

	/**
	 * Clone a remote repository. <br />
	 * @param uri is the path of remote project like: https://github.com/silvaaraujo/deploy2you.git
	 * @param baseProjectDir is the local path of project like: $HOME/temp/deploy2you
	 * @throws InvalidRemoteException
	 * @throws TransportException
	 * @throws GitAPIException
	 */
	public void cloneRepository(String uri, String baseProjectDir) throws InvalidRemoteException, TransportException, GitAPIException {
		Git.cloneRepository()
			.setURI(uri)
			.setDirectory(new File(baseProjectDir))
			.call();
	}
	
	/**
	 * Retrieve a existent repository. <br />
	 * @param repository is the local path of project that contains .git directory like: $HOME/temp/deploy2you/.git
	 * @return
	 * 		org.eclipse.jgit.lib.Repository
	 * @throws IOException
	 */
	public Repository getRepository(String repository) throws IOException {
		return new RepositoryBuilder()
			.setMustExist(true)
			.setGitDir(new File(repository))
			.build();
	}

	/**
	 * Retrieve the remote tags. <br />
	 * @param repository is the path of repository. 
	 * @return
	 * @throws Exception
	 */
	public List<String> getTags(String repository) throws Exception {
		List<String> tags = new ArrayList<>();
		try (Repository repo = this.getRepository(repository)) {
			try (Git git = new Git(repo)) {
				Collection<Ref> advertisedRefs = git.pull().call().getFetchResult().getAdvertisedRefs();
				for (Ref ref : advertisedRefs) {
					if (ref.getName().contains("refs/tags/")) {
						tags.add(ref.getName().substring(10));
					}
				}
			}
		}
		return tags;
	}
	
	/**
	 * Retrieve the remote branches. <br />
	 * @param repository is a instance of org.eclipse.jgit.lib.Repository. 
	 * @return
	 * @throws Exception
	 */
	public List<String> getBranches(String repository) throws Exception {
		List<String> branches = new ArrayList<>();
		try (Repository repo = this.getRepository(repository)) {
			try (Git git = new Git(repo)) {
				Collection<Ref> advertisedRefs = git.pull().call().getFetchResult().getAdvertisedRefs();
				for (Ref ref : advertisedRefs) {
					if (ref.getName().contains("refs/heads/")) {
						branches.add(ref.getName().substring(11));
					}
				}
			}
		}
		return branches;
	}
	
}