package br.lucasChagas.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.restassured.http.ContentType;

public class VerbosRestTest {
	
	@Test
	public void deveSalvarUsuario() {
		given()
			.contentType("application/json")
			.body("	{ \"name\": \"Jose\", \"age\": 50 }")
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Jose"))
			.body("age", is(50))
			;		
	}
	
	@Test
	public void naoDeveSalvarUsuarioSemNome() {
		given()
		.contentType(ContentType.JSON)
		.body("	{ \"age\": 50 }")
	.when()
		.post("https://restapi.wcaquino.me/users")
	.then()
		.statusCode(400)
		.body("id", is(nullValue()))
		.body("error", is("Name � um atributo obrigat�rio"))
		;	
	}
	
	@Test
	public void deveSalvarUsuarioViaXML() {	
		given()
			.contentType(ContentType.XML)
			.body("	<user> <name>Jose</name><age>50</age></user>}")
		.when()
			.post("https://restapi.wcaquino.me/usersXML")
		.then()
			.statusCode(201)
			.body("user.@id", is(notNullValue()))
			.body("user.name", is("Jose"))
			.body("user.age", is("50"))
			;		
	}
	
	@Test
	public void deveAlterarUsuario() {	
		given()
			.contentType(ContentType.JSON)
			.body("	{ \"name\": \"Usuario alterado\", \"age\": 80 }")
		.when()
			.put("https://restapi.wcaquino.me/users/1")
		.then()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuario alterado"))
			.body("age", is(80))
			.body("salary", is(1234.5678f))
			;		
	}
	
	@Test
	public void devoCustomizarURLParte1() {	
		given()
			.contentType(ContentType.JSON)
			.body("	{ \"name\": \"Usuario alterado\", \"age\": 80 }")
		.when()
			.put("https://restapi.wcaquino.me/{entidade}/{userId}", "users", "1")
		.then()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuario alterado"))
			.body("age", is(80))
			.body("salary", is(1234.5678f))
			;		
	}
	
	@Test
	public void devoCustomizarURLParte2() {
		given()
			.contentType(ContentType.JSON)
			.body("	{ \"name\": \"Usuario alterado\", \"age\": 80 }")
			.pathParam("entidade", "users")
			.pathParam("userId", 1)
		.when()
			.put("https://restapi.wcaquino.me/{entidade}/{userId}")
		.then()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuario alterado"))
			.body("age", is(80))
			.body("salary", is(1234.5678f))
			;		
	}
	
	@Test
	public void devoRemoverUsuario() {	
		given()
		.when()
			.delete("https://restapi.wcaquino.me/users/1")
		.then()
			.statusCode(204)
			;		
	}
	
	@Test
	public void devoRemoverUsuarioInexistente() {	
		given()
		.when()
			.delete("https://restapi.wcaquino.me/users/1000")
		.then()
			.statusCode(400)
			.body("error", is("Registro inexistente"))
			;		
	}
	
	@Test
	public void deveSalvarUsuarioUsandoMap() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "Usuario via map");
		params.put("age", 25);
		
		given()
			.contentType("application/json")
			.body(params)
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Usuario via map"))
			.body("age", is(25))
			;		
	}
	
	@Test
	public void deveSalvarUsuarioUsandObjeto() {
		User user = new User("Usuario via objeto", 35);
		
		given()
			.contentType("application/json")
			.body(user)
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Usuario via objeto"))
			.body("age", is(35))
			;		
	}
	
	@Test
	public void deveSalvarUsuarioViaXMLUsandoObjeto() {	
		User user = new User("Usuario XML", 40);
		
		given()
			.log().all()
			.contentType(ContentType.XML)
			.body(user)
		.when()
			.post("https://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(201)
			.body("user.@id", is(notNullValue()))
			.body("user.name", is("Usuario XML"))
			.body("user.age", is("40"))
	;
	}
	
	@Test
	public void deveDeserializarObjetoAoSalvarUsuario() {
		User user = new User("Usuario deserializado", 35);
		
		User usuarioInserido = given()
			.contentType("application/json")
			.body(user)
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.statusCode(201)
			.extract().body().as(User.class)
			;		
		
			assertThat(usuarioInserido.getId(), notNullValue());
			assertEquals("Usuario deserializado", usuarioInserido.getName());
			assertThat(usuarioInserido.getAge(), is(35));
	}
	
	@Test
	public void deveDeserializarXMLAoSalvarUsuario() {	
		User user = new User("Usuario XML", 40);
		
		User usuarioInserido = given()
			.log().all()
			.contentType(ContentType.XML)
			.body(user)
		.when()
			.post("https://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(201)
			.extract().body().as(User.class)
			;		
		assertThat(usuarioInserido.getId(), notNullValue());
		assertThat(usuarioInserido.getName(), is("Usuario XML"));
		assertThat(usuarioInserido.getAge(), is(40));
		assertThat(usuarioInserido.getSalary(), notNullValue());
	}
	
		
}
