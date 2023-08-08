package apitest.googlemaps;

import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class LibraryApi {
	@Test(dataProvider = "dp")
	public void libraryTest(String isbn, String aisle) {

		RestAssured.baseURI = "http://216.10.245.166";

		String response = given().log().all()
				.header("Content-Type", "application/json")
				.body(Payload.libraryPayLoad(isbn, aisle)).when()
				.post("/Library/Addbook.php").then().log().all().statusCode(200)
				.extract().asString();

		JsonPath js = new JsonPath(response);
		String id = js.getString("ID");
		System.out.println(id);

	}

	@DataProvider
	public Object[][] dp() {
		return new Object[][]{{"abcd", "1234"}, {"bdce", "2345"},
				{"cdef", "3456"}};
	}
}
