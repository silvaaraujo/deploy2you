package br.com.silvaaraujo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.bson.types.ObjectId;

import br.com.silvaaraujo.entidade.Usuario;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Model
public class UsuarioDAO {
	
	private final String collection = "usuario";
	private DBCollection dbCollection;
	
	@Inject
	private DB db;
	
	@PostConstruct
	public void init() {
		try {
			dbCollection = db.getCollection(collection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Usuario> findAll() {
		List<Usuario> lista = new ArrayList<>();
		DBCursor cursor = dbCollection.find();
		
		while (cursor.hasNext()) {
			DBObject resultElement = cursor.next();
			Map<?, ?> resultElementMap = resultElement.toMap();
			lista.add(recuperaEntidade(resultElementMap));
		}
		return lista;
	}
	
	public Usuario findById(String id) {
		DBObject dbObject = dbCollection.findOne(new BasicDBObject("_id", new ObjectId(id)));
		Map<?, ?> resultElementMap = dbObject.toMap();
		return recuperaEntidade(resultElementMap);
	}
	
	public void insert(Usuario usuario) {
		dbCollection.insert(getBasicDBOjectFromUsuario(usuario));
	}
	
	public void update(Usuario usuario) {
		BasicDBObject usuarioVelho = new BasicDBObject("_id", usuario.getId());
		BasicDBObject usuarioNovo = getBasicDBOjectFromUsuario(usuario);
		dbCollection.update(usuarioVelho, usuarioNovo, true, false);
	}
	
	public void removeById(ObjectId id) {
		dbCollection.remove(new BasicDBObject("_id", id));
	}
	
	public BasicDBObject getBasicDBOjectFromUsuario(Usuario usuario) {
		BasicDBObject basicDBObject = new BasicDBObject();
		basicDBObject.append("nome", usuario.getNome());
		basicDBObject.append("login", usuario.getLogin());
		basicDBObject.append("senha", usuario.getSenha());
		return basicDBObject;
	}
	
	private Usuario recuperaEntidade(Map<?, ?> resultElementMap) {
		Usuario u = new Usuario();
		u.setId((ObjectId) resultElementMap.get("_id"));
		u.setNome((String) resultElementMap.get("nome"));
		u.setLogin((String) resultElementMap.get("login"));
		u.setSenha((String) resultElementMap.get("senha"));
		return u;
	}

}