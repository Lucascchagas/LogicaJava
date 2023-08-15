package rest.tests.refac.suite;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import br.lcchagas.rest.core.BaseTest;
import io.restassured.RestAssured;
import rest.tests.refac.AuthTest;
import rest.tests.refac.ContasTest;
import rest.tests.refac.MovimentacaoTest;
import rest.tests.refac.SaldoTest;

@RunWith(org.junit.runners.Suite.class)
@SuiteClasses({
	ContasTest.class,
	MovimentacaoTest.class,
	SaldoTest.class,
	AuthTest.class
})
public class Suite extends BaseTest {

	@BeforeClass
	public static void login() {
		Map<String, String> login = new HashMap<>();
		login.put("email", "wagner@aquino");
		login.put("senha", "123456");
		
		String TOKEN = given()
			.body(login)
		.when()
			.post("/signin")
		.then()
			.statusCode(200)
			.extract().path("token");
		
		RestAssured.requestSpecification.header("Authorization", "JWT " + TOKEN);
		
		RestAssured.get("/reset").then().statusCode(200);
	}
}

