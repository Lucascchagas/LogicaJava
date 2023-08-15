package rest.tests.refac;

import org.junit.Test;

import io.restassured.specification.FilterableRequestSpecification;

import static io.restassured.RestAssured.*;

public class AuthTest {
	

	@Test
	public void naoDeveAcessarAPISemToken() {
		FilterableRequestSpecification req = (FilterableRequestSpecification) requestSpecification;
		req.removeHeader("Authorization");
		
		given()
		.when()
			.get("/contas")
		.then()
			.statusCode(401)
		;
	}

}
