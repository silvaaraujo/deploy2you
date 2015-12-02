package br.com.silvaaraujo.mb;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import com.mongodb.DB;
import com.mongodb.Mongo;

@ApplicationScoped
@Named("mbAppUtils")
public class MBAppUtils {

	private DB db;
	
	@Produces
	public DB getDb() {
		if (this.db == null) {
			Mongo mongo = new Mongo("localhost", 27017);
			this.db = mongo.getDB("deploy2you");
		}
		return db;
	}
	
}