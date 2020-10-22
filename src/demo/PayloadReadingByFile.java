package demo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payloadPackage.ReUsableMethods;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PayloadReadingByFile {
	
	
	@Test
	public void ReadPayloadFromFile() throws IOException {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String addResponse = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\kumars279\\eclipse-workspace\\RestAssuredTesting\\src\\files\\payload1"))))
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		System.out.println(addResponse);
		
		JsonPath js = ReUsableMethods.convertRawToJson(addResponse);
		String placeId = js.getString("place_id");
		System.out.println(placeId);
		
	}

}
