package rest.tests.refac;

import org.junit.Test;

import br.lcchagas.rest.core.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import rest.utils.BarrigaUtils;

public class ContasTest extends BaseTest {

	@Test
	public void deveIncluirContaComSucesso() {
		given()
			.body("{ \"nome\": \"Conta inserida\" }")
		.when()
			.post("/contas")
		.then()
			.statusCode(201)
		;
	}
	
	@Test
	public void deveAlterarContaComSucesso() {
		Integer CONTA_ID = BarrigaUtils.getIdContaPeloNome("Conta para alterar");
		
		given()
			.body("{ \"nome\": \"Conta alterada\" }")
			.pathParam("id", CONTA_ID)
		.when()
			.put("/contas/{id}")
		.then()
			.statusCode(200)
			.body("nome", is("Conta alterada"))
			
			;
	}
}
