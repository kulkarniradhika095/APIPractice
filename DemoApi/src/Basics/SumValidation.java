package Basics;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payloads;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	//Verify if Sum of all Course prices matches with Purchase Amount


	@Test
	public void sumOfCourses() {
		int sum = 0;
		JsonPath js = new JsonPath(Payloads.coursePrice());
		int count =	js.getInt("courses.size()");
		for (int i = 0; i < count; i++) {
			int prices =js.get("courses["+i+"].price");
			int copies =js.get("courses["+i+"].copies");
			int amt = prices * copies;
			System.out.println(amt);
			sum = sum+ amt;
		}
		System.out.println(sum);
		int purchaseAmount= js.get("dashboard.purchaseAmount");
		Assert.assertEquals(sum, purchaseAmount);
	}
}
