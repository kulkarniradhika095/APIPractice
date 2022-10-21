package Basics;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.testng.Assert;

import files.Payloads;
import files.ReusableMethods;

/**
 * Validate if Add Place API is working as expected
 * @author radhi
 * //given - all input details
 * //when - Submit the API - resource and http method
 * //Then - Validate the response
 */
public class Basics {
	public static void main(String[] args) throws IOException {
		//Basics.addPlace();
		//Basics.getResponse();
		//passingJsonPath();
		//updatePlace();
		//checkIfThePlaceIdIsUpdatedGetPlace();
		addPlaceUsingExternalFile();
		
	}
	public static void addPlace() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		given().log().all().queryParam("key", "qaclick12https://rahulshettyacademy.com/maps/api/place/add/json?key= qaclick123")
		.header("Content-Type","application/json")
		.body(Payloads.addPlace())
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200)
		.body("scope", equalTo("APP")).header("Server","Apache/2.4.41 (Ubuntu)")
		.extract().response().asString();
	}
	public static void getResponse() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick12https://rahulshettyacademy.com/maps/api/place/add/json?key= qaclick123")
		.header("Content-Type","application/json")
		.body(Payloads.addPlace())
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200)
		.body("scope", equalTo("APP")).header("Server","Apache/2.4.41 (Ubuntu)")
		.extract().response().asString();
		System.out.println("Responce is: "+response);
	}
	public static void passingJsonPath() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick12https://rahulshettyacademy.com/maps/api/place/add/json?key= qaclick123")
		.header("Content-Type","application/json")
		.body(Payloads.addPlace())
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200)
		.body("scope", equalTo("APP")).header("Server","Apache/2.4.41 (Ubuntu)")
		.extract().response().asString();
		System.out.println("Responce is: "+response);
		JsonPath json = new JsonPath(response);//for parsing JSON
		String placeId=json.getString("place_id");
		System.out.println("Place_Id= "+placeId);
	}
	public static void updatePlace() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick12https://rahulshettyacademy.com/maps/api/place/add/json?key= qaclick123")
				.header("Content-Type","application/json")
				.body(Payloads.addPlace())
				.when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server","Apache/2.4.41 (Ubuntu)")
				.extract().response().asString();
				System.out.println("Responce is: "+response);
				JsonPath json = new JsonPath(response);
				String placeId=json.getString("place_id");
				System.out.println("Place_Id= "+placeId);
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\"70 Summer walk, USA\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "")
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
	}
	public static void checkIfThePlaceIdIsUpdatedGetPlace() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick12https://rahulshettyacademy.com/maps/api/place/add/json?key= qaclick123")
				.header("Content-Type","application/json")
				.body(Payloads.addPlace())
				.when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server","Apache/2.4.41 (Ubuntu)")
				.extract().response().asString();
				JsonPath json = new JsonPath(response);
				String placeId=json.getString("place_id");
				System.out.println("Place_Id= "+placeId);
				
				String newAddress = "Summer Walk, Africa";
				given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
				.body("{\r\n"
						+ "\"place_id\":\""+placeId+"\",\r\n"
						+ "\"address\":\""+newAddress+"\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n"
						+ "}\r\n"
						+ "")
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));

		String getPlaceResponse= given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200)
		.extract().response().asString();
		 JsonPath js= ReusableMethods.rawToJson(getPlaceResponse);
		String actualAddress=js.getString("address");
		System.out.println(actualAddress);
		//Junit , TestNg
		Assert.assertEquals(actualAddress, newAddress);
	}
	public static void addPlaceUsingExternalFile() throws IOException {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick12https://rahulshettyacademy.com/maps/api/place/add/json?key= qaclick123")
		.header("Content-Type","application/json")
		.body(new String (Files.readAllBytes(Path.of("D:\\API\\files\\AddPlace.json"))))
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200)
		.body("scope", equalTo("APP")).header("Server","Apache/2.4.41 (Ubuntu)")
		.extract().response().asString();
		System.out.println(response);
	}
}
