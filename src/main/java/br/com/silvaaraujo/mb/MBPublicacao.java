package br.com.silvaaraujo.mb;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.silvaaraujo.dao.ConfiguracaoDAO;
import br.com.silvaaraujo.dao.ProjetoDAO;
import br.com.silvaaraujo.dao.PublicacaoDAO;
import br.com.silvaaraujo.entidade.Configuracao;
import br.com.silvaaraujo.entidade.Projeto;
import br.com.silvaaraujo.entidade.Publicacao;
import br.com.silvaaraujo.utils.DockerUtils;
import br.com.silvaaraujo.utils.GitUtils;

@ViewScoped
@Named("mbPublicacao")
public class MBPublicacao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PublicacaoDAO publicacaoDAO;
	
	@Inject
	private ProjetoDAO projetoDAO;
	
	@Inject
	private ConfiguracaoDAO configuracaoDAO;
	
	@Inject
	private GitUtils gitUtils;
	
	@Inject
	private DockerUtils dockerUtils;
	
	private Publicacao publicacao;
	
	private List<Projeto> projetos;
	
	private String projectId;
	
	private Configuracao configuracao;
	
	@PostConstruct
	public void init() {
		publicacao = new Publicacao();
		this.projetos = new ArrayList<>();
		this.configuracao = this.configuracaoDAO.buscarConfiguracao();
	}

	public void publicar() {
		RequestContext ctx = RequestContext.getCurrentInstance();
		
		if (!this.validar(ctx)) {
			return;
		}
		
		try {
			//localiza o projeto a ser publicado
			Projeto projeto = this.projetoDAO.findById(this.projectId);
			
			//cria o objeto da publicação o qual contera informacoes sobre
			//o projeto, tag e caminho de acesso para a aplicação web.
			this.criarPublicacao(projeto);

			//grava a publicacao 
			this.publicacaoDAO.insert(this.publicacao);
			
			//cria o container do docker
			createContainer(this.publicacao, projeto);
			
			//limpa as variaveis para permitir a insercao de uma nova publicacao
			this.limpar();
		} catch (Exception e) {
			ctx.execute("alerta.erro('Erro ao efetuar publicação, favor informar a administração do sistema.');");
			return;
		}

		ctx.execute("alerta.sucesso('Publicação efetuada com sucesso!');");
	}
	
	public void createContainer(Publicacao publicacao, Projeto projeto) {
		this.dockerUtils.createContainer(publicacao, projeto);
	}

	private boolean validar(RequestContext ctx) {
		Boolean valido = Boolean.TRUE;
		
		if (this.projectId == null || this.projectId.trim().isEmpty()) {
			ctx.execute("alerta.erro('O projeto é obrigatório.');");
			valido = Boolean.FALSE;
		}

		if (this.publicacao.getTag() == null || this.publicacao.getTag().trim().isEmpty()) {
			ctx.execute("alerta.erro('A tag é obrigatória.');");
			valido = Boolean.FALSE;
		}

		if (valido) {
			valido = this.validarTag(ctx);
		}

		return valido;
	}

	private void criarPublicacao(Projeto p) {

		if (p == null) {
			return;
		}
		
		this.publicacao.setAtivo(Boolean.TRUE);
		this.publicacao.setData(new Date());
		this.publicacao.setProjeto(p.getNome());
		this.publicacao.setUser("admin"); //usuario logado 
		this.publicacao.setContainer(p.getNomeBaseContainer() + "-" + this.publicacao.getTag());
		
		List<Integer> ports = this.getPorts(p);
		this.publicacao.setUsedPorts(ports);
		this.publicacao.setUrl(MessageFormat.format("https://{0}:{1}/{2}",
													this.configuracao.getNomeHost(),
													ports.get(0).toString(),
													p.getContextoWeb()));
	}
	
	private List<Integer> getPorts(Projeto projeto) {
		//portas indisponiveis
		List<Integer> unavailablePorts = getUnavailablePorts();

		//lista que sera retornada para ser setada na publicacao
		List<Integer> ports = new ArrayList<>();
		
		//para cada porta que o projeto expoe, executa um randon para definir a proxima porta setada
		//caso o randon retorne uma porta ja utilizada, uma nova chamada ao metodo randomico 
		//eh executada ate que se consiga uma porta disponivel.
		String[] portas = projeto.getPortas().split(",");
		for (int i = 0; i < portas.length; i++) {
			Integer nextPort = getNextPort(unavailablePorts);
			ports.add(nextPort);
			unavailablePorts.add(nextPort);
		}
		
		return ports;
	}

	private List<Integer> getUnavailablePorts() {
		List<Integer> unavailablePorts = new ArrayList<>();
		
		for (Publicacao publicacao : this.publicacaoDAO.findAll()) {
			unavailablePorts.addAll(publicacao.getUsedPorts());
		}
		
		return unavailablePorts;
	}

	private int getNextPort(List<Integer> unavailablePorts) {
		Integer nextPort = null;
		
		while (nextPort == null) {
			nextPort = ThreadLocalRandom.current().nextInt(8000, 9900 + 1);
			if (!isValidPort(nextPort, unavailablePorts)) {
				nextPort = null;
			}
		}
		
		return nextPort;
	}
	
	private boolean isValidPort(Integer nextPort, List<Integer> unavailablePorts) {
		if (unavailablePorts.contains(nextPort)) {
			return false;
		}
		return true;
	}

	/*	
	private int getPorta() {
		List<Publicacao> list = this.publicacaoDAO.findAll();
		int qtdPublicada = list != null ? list.size() : 0;
		qtdPublicada++;
		int porta = 8080+qtdPublicada;
		return porta; 
	}
*/
	private void limpar() {
		this.publicacao = new Publicacao();
	}

	public Publicacao getPublicacao() {
		return publicacao;
	}

/*	
	@GET
	@Path("tags")
	@Produces({ MediaType.APPLICATION_JSON})
	public Tags getTags(@QueryParam("projetoId") String projetoId) {

		if (projetoId == null || projetoId.trim().isEmpty()) {
			return new Tags();
		}
		
		Projeto projeto = this.projetoDAO.findById(projetoId);
		List<String> lista = null;
		
		try {
			lista = this.gitUtils.getTags(projeto.getRepositorioGit());
			if (lista == null || lista.isEmpty()) {
				return new Tags();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Tags();
		}
		
		Tags tags = new Tags();
		for (String s : lista) {
			tags.getTag().add(s);
		}
		
		return tags;
	}
*/
	
	public List<Projeto> getProjetos() {
		if (this.projetos == null || this.projetos.isEmpty()) {
			 this.projetos.addAll(this.projetoDAO.findAll());
		}
		return projetos;
	}
	
	public void projetoChanged() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String x =String.valueOf(params.get("id"));
		this.projectId = x;
	}
	
	public boolean validarTag(RequestContext ctx) {
		Projeto projeto = this.projetoDAO.findById(this.projectId);

		if (projeto == null || this.configuracao == null) {
			return false;
		}
		
		List<String> tags = null;
		try {
			 tags = this.gitUtils.getRemoteTags(projeto.getRepositorioGit(), 
												this.configuracao.getUsuarioGit(), 
												this.configuracao.getPasswordUsuarioGit());
		
			if (tags == null || tags.isEmpty()) {
				ctx.execute("alerta.erro('A tag informada não foi encontrada.');");
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		boolean contains = tags.contains(this.publicacao.getTag());
		
		if (!contains) {
			ctx.execute("alerta.erro('A tag informada não foi encontrada.');");
		}
		
		return contains;
	}
}