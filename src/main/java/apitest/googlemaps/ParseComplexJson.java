package apitest.googlemaps;

import org.testng.Assert;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ParseComplexJson {

	public static void main(String[] args) {

		JsonPath js = new JsonPath(Payload.coursePrice());

		int numberOfCourses = js.getInt("courses.size()");
		System.out.println(numberOfCourses);

		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);

		String firstTitle = js.getString("courses[0].title");
		System.out.println(firstTitle);

		String thirdTitle = js.getString("courses[2].title");
		System.out.println(thirdTitle);

		// Print all course titles and prices

		for (int i = 0; i < numberOfCourses; i++) {

			String courseTitle = js.get("courses[" + i + "].title");
			int coursePrice = js.get("courses[" + i + "].price");
			System.out.println(courseTitle);
			System.out.println(coursePrice);
		}

		// Print number of copies sold by RPA Course
		System.out.println("Print number of copies sold by RPA Course");

		for (int i = 0; i < numberOfCourses; i++) {

			String courseTitle = js.get("courses[" + i + "].title");

			if (courseTitle.equalsIgnoreCase("RPA")) {

				int copies = js.getInt("courses[" + i + "].copies");
				System.out.println(copies);
				break;
			}

		}

		// Verify Sum of all prices equals to purchase amount

		System.out
				.println("Verify Sum of all prices equals to purchase amount");

		int count = 0;

		for (int i = 0; i < numberOfCourses; i++) {

			int coursePrice = js.get("courses[" + i + "].price");
			int copies = js.get("courses[" + i + "].copies");
			count = count + (coursePrice * copies);
		}

		int purchaseAmount = js.getInt("dashboard.purchaseAmount");

		Assert.assertEquals(count, purchaseAmount);
		System.out.println("Assertion Successful");

	}

}
