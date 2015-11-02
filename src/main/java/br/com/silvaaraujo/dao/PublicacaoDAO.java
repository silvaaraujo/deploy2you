package br.com.silvaaraujo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.bson.types.ObjectId;

import br.com.silvaaraujo.entidade.Publicacao;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class PublicacaoDAO {
	
	private final String collection = "publicacao";
	private DBCollection publicacaoCollection;
	
	@Inject
	private DB db;
	
	@PostConstruct
	public void init() {
		try {
			publicacaoCollection = db.getCollection(collection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Publicacao> findAll() {
		
		List<Publicacao> listaPublicacao = new ArrayList<>();
		DBCursor cursor = publicacaoCollection.find();
		
		while (cursor.hasNext()) {
			DBObject resultElement = cursor.next();
			
			Map<?, ?> resultElementMap = resultElement.toMap();
			Publicacao publicacao = recuperaPublicacaoMap(resultElementMap);
			
			listaPublicacao.add(publicacao);
		}
		
		return listaPublicacao;
	}

	public Publicacao findById(String id) {
		
		DBObject dbObject = publicacaoCollection.findOne(new BasicDBObject("_id", new ObjectId(id)));
		Map<?, ?> resultElementMap = dbObject.toMap();
		
		return recuperaPublicacaoMap(resultElementMap);
	}
	
	public void insert(Publicacao publicacao) {
		
		BasicDBObject publicacaoParaGravar = criarPublicacaoParaGravar(publicacao);
		publicacaoCollection.insert(publicacaoParaGravar);
		
	}
	
	public void update(Publicacao publicacao) {
		
		BasicDBObject publicacaoOld = new BasicDBObject("_id", publicacao.getId());
		BasicDBObject publicacaoParaGravar = criarPublicacaoParaGravar(publicacao);
		publicacaoCollection.update(publicacaoOld, publicacaoParaGravar,true, false);
		
	}
	
	public void removeById(ObjectId id) {
		publicacaoCollection.remove(new BasicDBObject("_id", id));
		
	}
	
	public BasicDBObject criarPublicacaoParaGravar(Publicacao publicacao) {
		
		BasicDBObject publicacaoParaGravar = new BasicDBObject();
		publicacaoParaGravar.append("projeto", publicacao.getProjeto());
		publicacaoParaGravar.append("tag", publicacao.getTag());
		publicacaoParaGravar.append("url", publicacao.getUrl());
		publicacaoParaGravar.append("user", publicacao.getUser());
		publicacaoParaGravar.append("ativo", publicacao.getAtivo());
		publicacaoParaGravar.append("branch", publicacao.getBranch());
		publicacaoParaGravar.append("data", publicacao.getData());
		
		return publicacaoParaGravar;
	}
	
	private Publicacao recuperaPublicacaoMap(Map<?, ?> resultElementMap) {
		
		Publicacao publicacao = new Publicacao();
		
		publicacao.setId((ObjectId) resultElementMap.get("_id"));
		publicacao.setProjeto((String) resultElementMap.get("projeto"));
		publicacao.setTag((String) resultElementMap.get("tag"));
		publicacao.setUrl((String) resultElementMap.get("url"));
		publicacao.setUser((String) resultElementMap.get("user"));
		publicacao.setAtivo((Boolean) resultElementMap.get("ativo"));
		publicacao.setBranch((String) resultElementMap.get("branch"));
		publicacao.setData((Date) resultElementMap.get("data"));
		
		return publicacao;
	}
}
