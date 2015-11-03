package br.com.silvaaraujo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.bson.types.ObjectId;

import br.com.silvaaraujo.entidade.Projeto;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ProjetoDAO {
	
	private final String collection = "projeto";
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
	
	public List<Projeto> findAll() {
		List<Projeto> lista = new ArrayList<>();
		DBCursor cursor = dbCollection.find();

		while (cursor.hasNext()) {
			DBObject resultElement = cursor.next();

			Map<?, ?> resultElementMap = resultElement.toMap();
			lista.add(recuperaEntidade(resultElementMap));
		}
		return lista;
	}

	public Projeto findById(String id) {
		DBObject dbObject = dbCollection.findOne(new BasicDBObject("_id", new ObjectId(id)));
		Map<?, ?> resultElementMap = dbObject.toMap();
		return recuperaEntidade(resultElementMap);
	}
	
	public void insert(Projeto projeto) {
		dbCollection.insert(getBasicDBOjectFromProjeto(projeto));
	}

	public void update(Projeto projeto) {
		BasicDBObject projetoVelho = new BasicDBObject("_id", projeto.getId());
		BasicDBObject projetoNovo = getBasicDBOjectFromProjeto(projeto);
		dbCollection.update(projetoVelho, projetoNovo, true, false);
	}
	
	public void removeById(ObjectId id) {
		dbCollection.remove(new BasicDBObject("_id", id));
	}
	
	public BasicDBObject getBasicDBOjectFromProjeto(Projeto projeto) {
		BasicDBObject basicDBObject = new BasicDBObject();
		basicDBObject.append("nome", projeto.getNome());
		basicDBObject.append("repositorioGit", projeto.getRepositorioGit());
		basicDBObject.append("nomeImagemDocker", projeto.getNomeImagemDocker());
		return basicDBObject;
	}
	
	private Projeto recuperaEntidade(Map<?, ?> resultElementMap) {
		Projeto p = new Projeto();
		p.setId((ObjectId) resultElementMap.get("_id"));
		p.setNome((String) resultElementMap.get("nome"));
		p.setRepositorioGit((String) resultElementMap.get("repositorioGit"));
		p.setNomeImagemDocker((String) resultElementMap.get("nomeImagemDocker"));
		return p;
	}
}