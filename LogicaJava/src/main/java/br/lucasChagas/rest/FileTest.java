package br.lucasChagas.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;


public class FileTest {	

	@Test
	public void deveObrigarEnvioArquivo() {
		given()
			.log().all()
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(400)
			.body("error", is("Arquivo não enviado"))
			;		
		}
	
	@Test
	public void deveRealizarUploadDeArquivo() {
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/users.pdf"))
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("users.pdf"))
			;		
		}
	
	@Test
	public void naoDeveRealizarUploadDeArquivoGrande() {
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/AlbertM.Wolters.pdf"))
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.time(lessThan(5000L))
			.statusCode(413)
			;		
		}
	
	@Test
	public void deveRealizarDownloadDeArquivo() throws IOException {
		byte[] image = given()
				.log().all()
			.when()
				.get("http://restapi.wcaquino.me/download")
			.then()
				.statusCode(200)
				.extract().asByteArray();
			;
			File imagem = new File("src/main/resources/file.jpg");
			OutputStream out = new FileOutputStream(imagem);
			out.write(image);
			out.close();
			
			assertThat(imagem.length(), lessThan(100000L));
		 }
}
