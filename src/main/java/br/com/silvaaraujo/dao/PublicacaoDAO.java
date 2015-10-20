package br.com.silvaaraujo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import br.com.silvaaraujo.entidade.Publicacao;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class PublicacaoDAO {
	
	private final String host = "localhost";
	private final int port = 27017;
	private final String database = "deploy2you";
	private final String collection = "publicacao";
	
	private DBCollection publicacaoCollection;
	
	public Mongo mongo() throws Exception {
		MongoClient mongoClient = new MongoClient(new ServerAddress(host, port));
		return mongoClient;
	}
	
	public PublicacaoDAO() {
		try {
			Mongo mongo = new Mongo(host, port);
			DB db = mongo.getDB(database);
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
			Publicacao publicacao = new Publicacao();
			publicacao.setId((Long) resultElementMap.get("_id"));
			publicacao.setProjeto((String) resultElement.get("projeto"));
			publicacao.setTag((String) resultElement.get("tag"));
			publicacao.setUrl((String) resultElement.get("url"));
			publicacao.setUser((String) resultElementMap.get("user"));
			publicacao.setAtivo((Boolean) resultElement.get("ativo"));
			publicacao.setBranch((String) resultElement.get("branch"));
			publicacao.setData((Date) resultElement.get("data"));
			
			listaPublicacao.add(publicacao);
		}
		
		return listaPublicacao;
	}
	
	public static void main(String[] args) {
		PublicacaoDAO dao = new PublicacaoDAO();
		List<Publicacao> lista = dao.findAll();
		
		for (Publicacao publicacao : lista) {
			System.out.println(publicacao.getId());
		}
	}
}
