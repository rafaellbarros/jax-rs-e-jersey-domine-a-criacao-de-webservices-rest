package br.com.alura.loja;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

public class ClientTest {
	private HttpServer server;
	private Client client;

	@Before
	public void startaServidor() {
		server = Servidor.inicializaServidor();
		client = ClientBuilder.newClient();
	} 
	
	@After
	public void mataServidor() {
		server.stop();
	}

	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
		WebTarget target = client.target("http://localhost:8080");
		String conteudo = target.path("/carrinhos/1").request().get(String.class);

		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		
		assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
	}
	
	@Test
	public void testaQueSuportaNovosCarrinhos(){
		WebTarget target = client.target("http://localhost:8080");
		Carrinho carrinho = new Carrinho();
		carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
		carrinho.setRua("Rua Vergueiro");
		carrinho.setCidade("Sao Paulo");
		String xml = carrinho.toXML();
		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
		
		String conteudo = target.path("/carrinhos").request().post(entity, String.class);
		assertEquals("<status>sucesso</status>", conteudo);
	}
}
