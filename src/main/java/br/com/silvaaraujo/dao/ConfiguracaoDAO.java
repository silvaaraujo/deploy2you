package br.com.silvaaraujo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.bson.types.ObjectId;

import br.com.silvaaraujo.entidade.Configuracao;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Model
public class ConfiguracaoDAO {
	
	private final String collection = "configuracao";
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
	
	public List<Configuracao> findAll() {
		List<Configuracao> lista = new ArrayList<>();
		DBCursor cursor = dbCollection.find();

		while (cursor.hasNext()) {
			DBObject resultElement = cursor.next();

			Map<?, ?> resultElementMap = resultElement.toMap();
			lista.add(recuperaEntidade(resultElementMap));
		}
		return lista;
	}

	public Configuracao findById(String id) {
		DBObject dbObject = dbCollection.findOne(new BasicDBObject("_id", new ObjectId(id)));
		Map<?, ?> resultElementMap = dbObject.toMap();
		return recuperaEntidade(resultElementMap);
	}
	
	public void insert(Configuracao configuracao) {
		dbCollection.insert(this.getBasicDBOjectFromEntity(configuracao));
	}

	public void update(Configuracao configuracao) {
		BasicDBObject configVelho = new BasicDBObject("_id", configuracao.getId());
		BasicDBObject configNovo = getBasicDBOjectFromEntity(configuracao);
		dbCollection.update(configVelho, configNovo, true, false);
	}
	
	public void removeById(ObjectId id) {
		dbCollection.remove(new BasicDBObject("_id", id));
	}
	
	private BasicDBObject getBasicDBOjectFromEntity(Configuracao configuracao) {
		BasicDBObject basicDBObject = new BasicDBObject();
		basicDBObject.append("diretorioBase", configuracao.getDiretorioBase());
		basicDBObject.append("usuarioGit", configuracao.getUsuarioGit());
		basicDBObject.append("passwordUsuarioGit", configuracao.getPasswordUsuarioGit());
		return basicDBObject;
	}
	
	private Configuracao recuperaEntidade(Map<?, ?> resultElementMap) {
		Configuracao c = new Configuracao();
		c.setId((ObjectId) resultElementMap.get("_id"));
		c.setDiretorioBase((String) resultElementMap.get("diretorioBase"));
		c.setUsuarioGit((String) resultElementMap.get("usuarioGit"));
		c.setPasswordUsuarioGit((String) resultElementMap.get("passwordUsuarioGit"));
		return c;
	}
	
	public Configuracao buscarConfiguracao() {
		DBObject dbObject = dbCollection.findOne();
		
		if (dbObject == null) {
			return null;
		}
		
		Map<?, ?> resultElementMap = dbObject.toMap();
		return recuperaEntidade(resultElementMap);
	}
}