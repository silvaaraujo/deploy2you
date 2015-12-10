package br.com.silvaaraujo.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.bson.types.ObjectId;

import br.com.silvaaraujo.dao.UsuarioDAO;
import br.com.silvaaraujo.entidade.Usuario;

@ViewScoped
@Named("mbUsuario")
public class MBUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioDAO usuarioDAO;
	
	private List<Usuario> usuarios;
	private Usuario usuario;
	
	private String usuarioId;
	
	@PostConstruct
	private void init() {
		limpar();
	}
	
	public String novo() {
		this.limpar();
		return "cadastrarusuario";
	}
	
	public void limpar() {
		this.usuario = new Usuario();
		this.usuarios = new ArrayList<>();
	}

	public List<Usuario> getUsuarios() {
		if (this.usuarios == null || this.usuarios.isEmpty()) {
			this.usuarios = new ArrayList<>();
			this.pesquisarUsuarios();
		}
		return usuarios;
	}

	private void pesquisarUsuarios() {
		this.usuarios.addAll(this.usuarioDAO.findAll());
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setUsuarioId() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String x = String.valueOf(params.get("id"));
		this.usuarioId = x;
	}
	
	public String editar() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String x = String.valueOf(params.get("id"));
		
		if (x == null || "".equals(x)) {
			return "usuarios";
		}
		
		String param ="?faces-redirect=true&amp;userId="+x;
		
		return "cadastrousuario.xhtml"+param;
	}
	
	public void remover() {
		if (this.usuarioId == null) {
			return;
		}

		this.usuarioDAO.removeById(new ObjectId(this.usuarioId));
		this.limpar();
		pesquisarUsuarios();
	}
}