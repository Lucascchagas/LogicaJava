package br.lucasChagas.rest;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class TestJUnit {

	@Test
	public void testJUnit() {
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me:80/ola");
		Assert.assertEquals(200, response.statusCode());

		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
	}

	@Test
	public void adaptandoGherkin() {

		// Pré-condições
		given()
		// Ação
		.when()
			.get("http://restapi.wcaquino.me:80/ola")
		// Assertivas
		.then()
			.statusCode(200);
	}

	@Test
	public void aplicandoHamcrest() {
		assertThat("Maria", is("Maria"));
		assertThat("Maria", not("Amanda"));
		assertThat("Maria", anyOf(is("Maria"), is("Joaquina")));
		assertThat("Joaquina", allOf(startsWith("Joa"), endsWith("ina"), containsString("qui")));
		assertThat(128, is(128));
		assertThat(128, isA(Integer.class));
		assertThat(128d, isA(Double.class));
		assertThat(128d, greaterThan(120d));

		List<Integer> impares = Arrays.asList(1, 3, 5, 7, 9);
		assertThat(impares, hasSize(5));
		assertThat(impares, contains(1, 3, 5, 7, 9));
	}

	@Test
	public void validandoBody() {

		given()
		.when()
			.get("http://restapi.wcaquino.me:80/ola")
		.then().statusCode(200)
			.body(is("Ola Mundo!"))
			.body(containsString("Mundo"))
			.body(is(not(nullValue())));
	}
}
