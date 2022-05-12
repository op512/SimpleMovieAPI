package uk.co.op.movieapi;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import io.restassured.http.ContentType;
import uk.co.op.movieapi.controller.MovieController;
import uk.co.op.movieapi.controller.domain.Director;
import uk.co.op.movieapi.controller.domain.Movie;

@SpringBootTest(classes = MovieApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MovieApiApplicationTests {

	@MockBean
	MovieController movieController;

	private final static String BASE_URI = "http://localhost:8087/movie";

	@Test
	void testSecurity() {
		given().baseUri(BASE_URI + "/22").get().then().statusCode(401);
		given().baseUri(BASE_URI + "/22").patch().then().statusCode(401);
		given().baseUri(BASE_URI).post().then().statusCode(401);
		given().baseUri(BASE_URI + "/22").delete().then().statusCode(401);

		given().baseUri(BASE_URI + "/22").header("x-api-key", "userkey").patch().then().statusCode(403);
		given().baseUri(BASE_URI + "/22").header("x-api-key", "userkey").delete().then().statusCode(403);
		given().baseUri(BASE_URI).header("x-api-key", "userkey").post().then().statusCode(403);

		given().baseUri(BASE_URI + "/22").header("x-api-key", "adminkey").body(new Movie(null, "abc", null, null))
				.contentType(ContentType.JSON).patch().then().statusCode(200);
		given().baseUri(BASE_URI + "/22").header("x-api-key", "adminkey").delete().then().statusCode(200);
		given().baseUri(BASE_URI).header("x-api-key", "adminkey").contentType(ContentType.JSON)
				.body(new Movie(null, "abc", new Director(1, null, null), 2000)).post().then().statusCode(200);
	}
	


}
