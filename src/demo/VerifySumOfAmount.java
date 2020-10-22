package demo;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import payloadPackage.PayLoad;

public class VerifySumOfAmount {
	
	@Test
	public void SumOfAmount() {
		int sum = 0;
		JsonPath js = new JsonPath(PayLoad.CoursePrice());
		int count = js.getInt("course.size()");
		for(int i=0; i<count; i++) {
			int price = js.getInt("course["+i+"].price");
			int copies = js.getInt("course["+i+"].copies");
			int amount = price * copies;
			System.out.println(amount);
			sum = sum + amount;
		}
		System.out.println(sum);
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(sum, totalAmount);
	}
	

}
