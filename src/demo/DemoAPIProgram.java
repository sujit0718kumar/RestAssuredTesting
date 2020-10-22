package demo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payloadPackage.PayLoad;
import payloadPackage.ReUsableMethods;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

public class DemoAPIProgram {

	public static void main(String[] args) {
		
		//Scenario:- Add place-> Update place with new Address -> Get place to validate if new address is present in reponse
		
		
		//Add details and submit API then validate the response
		RestAssured.baseURI="https://rahulshettyacademy.com";
		System.out.println("----------Add details-------------");
		String addResponse= given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(PayLoad.AddPlace())
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		System.out.println(addResponse);
		
		JsonPath js = new JsonPath(addResponse);  //for parsing Json
		String placeId = js.getString("place_id");
		System.out.println(placeId);
		
		//Upadate place details
		String newAddress ="7070 UT USA";
		System.out.println("------------Update Details--------------");
		String putResponse = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n" + 
				"    \"place_id\": \""+placeId+"\",\r\n" + 
				"    \"address\": \""+newAddress+"\",\r\n" + 
				"    \"key\": \"qaclick123\"\r\n" + 
				"}")
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated")).extract().response().asString();
		
		
		JsonPath js1 = ReUsableMethods.convertRawToJson(putResponse);  // coverting raw to json using reusable method
		String msg =js1.getString("msg");
		System.out.println(msg);
		
		//Get Place
		System.out.println("------------Get Details------------------");
		String getResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).body("address", equalTo(newAddress)).extract().response().asString();
		
		JsonPath js2 = ReUsableMethods.convertRawToJson(getResponse);   // coverting raw to json using reusable method
		String actualAddress = js2.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, newAddress);
		
		
		
		

	}

}
