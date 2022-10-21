package Basics;


import files.Payloads;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
	public static void main(String[] args) {
		/*
		 * 1. Print No of courses returned by API
		 * 2. Print Purchase Amount
		 * 3. Print Title of the first course
		 * 4. Print All course titles and their respective Prices
		 * 5. Print no of copies sold by RPA Course
		 */
		JsonPath js = new JsonPath(Payloads.coursePrice());
		// 1. Print No of courses returned by API
		int count =	js.getInt("courses.size()");
		System.out.println(count);
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		//2. Print Purchase Amount
		System.out.println(totalAmount);
		//3. Print Title of the first course
		String titleFirstCourse =js.get("courses[0].title");
		String titleSecondCourse =js.get("courses[1].title");
		String titleThirdCourse =js.get("courses[2].title");
		System.out.println(titleFirstCourse);
		System.out.println(titleSecondCourse);
		System.out.println(titleThirdCourse);
		//4. Print All course titles and their respective Prices
		for (int i = 0; i < count; i++) {
			String titles =js.get("courses["+i+"].title");
			int prices =js.get("courses["+i+"].price");
			int copies =js.get("courses["+i+"].copies");
			System.out.println(titles+"\t"+prices+"\t"+copies);
		}
		//5. Print no of copies sold by RPA Course
		for (int i = 0; i < count; i++) {
			String titles =js.get("courses["+i+"].title");
			if (titles.equalsIgnoreCase("RPA") ) {
				int copies =js.get("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
			
		}
		
	}

	
	

}
