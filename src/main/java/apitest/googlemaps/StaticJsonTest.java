package apitest.googlemaps;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class StaticJsonTest {

	public static void main(String[] args) throws IOException {

		RestAssured.baseURI = "http://rahulshettyacademy.com";

		// Add Place

		String payload = new String(Files.readAllBytes(Paths.get("src/main/java/files/data.json")));
		String response = given().log().all().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json").body(payload).when()
				.post("maps/api/place/add/json").then().log().all().statusCode(200)
				.body("scope", equalTo("APP")).header("server", "Apache/2.4.52 (Ubuntu)").extract()
				.response().asString();

		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String placeId = js.getString("place_id");
		System.out.println("Place ID is : " + placeId);

	}

}
