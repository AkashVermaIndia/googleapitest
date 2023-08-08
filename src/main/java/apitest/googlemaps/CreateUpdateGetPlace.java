package apitest.googlemaps;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class CreateUpdateGetPlace {

	public static void main(String[] args) {

		RestAssured.baseURI = "http://rahulshettyacademy.com";

		// Add Place

		String response = given().log().all().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json")
				.body(Payload.addPlace()).when().post("maps/api/place/add/json")
				.then().log().all().statusCode(200)
				.body("scope", equalTo("APP"))
				.header("server", "Apache/2.4.52 (Ubuntu)").extract().response()
				.asString();

		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String placeId = js.getString("place_id");
		System.out.println("Place ID is : " + placeId);

		// Update Place

		String address = "29, side layout, cohen 10";

		given().log().all().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json")
				.body(Payload.updatePlace(placeId, address)).when()
				.put("maps/api/place/update/json").then().assertThat().log()
				.all().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		// Get Place

		String getPlaceResponse = given().log().all()
				.queryParam("key", "qaclick123").queryParam("place_id", placeId)
				.when().get("maps/api/place/get/json").then().log().all()
				.statusCode(200).extract().response().asString();

		JsonPath jsget = new JsonPath(getPlaceResponse);
		String newAddress = jsget.getString("address");
		Assert.assertEquals(newAddress, address);
		System.out.println("Assertion Successful");

	}

}
