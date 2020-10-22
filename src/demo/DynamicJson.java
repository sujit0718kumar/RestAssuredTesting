package demo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payloadPackage.PayLoad;
import payloadPackage.ReUsableMethods;

import static io.restassured.RestAssured.*;

public class DynamicJson {
	
	@Test(dataProvider="dataBook")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI="http://216.10.245.166";
		String response = given().log().all().header("Content-Type","application/json").body(PayLoad.AddBook(isbn,aisle))
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		JsonPath js = ReUsableMethods.convertRawToJson(response);
		String id= js.get("ID");
		System.out.println(id);
		String msg = js.getString("Msg");
		System.out.println(msg);
		
		
		// write delete code below (after adding data delete that data then again add data) 
	}
	
	@DataProvider(name="dataBook")
	public Object[][] getData() {
		return new Object[][] {{"Sujit","a123"},{"kumar","b123"},{"Alka","c123"},{"Vicky","d123"}};
	}
	

}
