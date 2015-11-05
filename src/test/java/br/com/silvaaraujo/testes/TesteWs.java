package br.com.silvaaraujo.testes;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

public class TesteWs {

	@Test
	public void testaAdicionaPos() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/deploy2you/resources");
		Response codigoRetorno = target.path("/manterPublicacao/tags").request().get(Response.class);
		
		client.close();
		Assert.assertEquals(200, codigoRetorno.getStatus());
	}
	
}
