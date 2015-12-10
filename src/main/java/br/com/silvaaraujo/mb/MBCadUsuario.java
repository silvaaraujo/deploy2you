package br.com.silvaaraujo.mb;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.silvaaraujo.dao.UsuarioDAO;
import br.com.silvaaraujo.entidade.Usuario;

@ViewScoped
@Named("mbCadUsuario")
public class MBCadUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioDAO usuarioDAO;
	
	private Usuario usuario;

	private String usuarioId;
	
	@PostConstruct
	public void init() {
		this.usuario = new Usuario();
	}
	
	public void gravar() {
		RequestContext ctx = RequestContext.getCurrentInstance();
		
		if (!validar(ctx)) {
			return;
		}
		
		this.usuarioDAO.insert(this.usuario);
		this.limpar();
		
		ctx.execute("alerta.sucesso('Usuário gravado com sucesso!');");
	}
	
	public void limpar() {
		this.usuarioId = null;
		this.usuario = new Usuario();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	private boolean validar(RequestContext ctx) {
		Boolean valido = Boolean.TRUE;
		
		if (this.usuario == null) {
			ctx.execute("alerta.erro('Usuário nulo, favor informar a administração do sistema.');");
			valido = Boolean.FALSE;
		}
		
		if (this.usuario.getNome() == null || "".equals(this.usuario.getNome().trim())) {
			ctx.execute("alerta.erro('O nome é obrigatório.');");
			valido = Boolean.FALSE;
		}
		
		if (this.usuario.getLogin() == null || "".equals(this.usuario.getLogin().trim())) {
			ctx.execute("alerta.erro('O login é obrigatório.');");
			valido = Boolean.FALSE;
		}
		
		return valido;
	}

	public String getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
		this.buscarUsuario();
	}
	
	private void buscarUsuario() {
		if (this.usuarioId != null || !this.usuarioId.trim().isEmpty()) {
			this.usuario = this.usuarioDAO.findById(this.usuarioId);
		}
	}
}