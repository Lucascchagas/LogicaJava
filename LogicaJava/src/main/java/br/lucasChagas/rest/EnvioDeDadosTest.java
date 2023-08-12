package br.lucasChagas.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import io.restassured.http.ContentType;

public class EnvioDeDadosTest {
	
	@Test
	public void deveEnviarValorViaQuery() {
		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/v2/users?format=xml")
		.then()	
			.log().all()
			.statusCode(200)
		    .contentType(ContentType.XML)
		;
	}
	
	@Test
	public void deveEnviarValorViaQueryViaParam() {
		given()
			.log().all()
			.queryParam("format", "xml")
		.when()
			.get("https://restapi.wcaquino.me/v2/users")
		.then()	
			.log().all()
			.statusCode(200)
		    .contentType(ContentType.XML)
		    .contentType(containsString("utf-8"))
		;
	}
	
	@Test
	public void deveEnviarValorViaHeader() {
		given()
			.log().all()
			.accept(ContentType.XML)
		.when()
			.get("https://restapi.wcaquino.me/v2/users")
		.then()	
			.log().all()
			.statusCode(200)
		    .contentType(ContentType.XML)
		;
	}

}
