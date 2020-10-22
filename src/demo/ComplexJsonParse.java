package demo;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import payloadPackage.PayLoad;

public class ComplexJsonParse {

	public static void main(String[] args) {
		
		JsonPath js =new JsonPath(PayLoad.CoursePrice());
		
		//1. Print No of courses returned by API
		
		int courseCount = js.getInt("course.size()");
		System.out.println(courseCount);

		//2.Print Purchase Amount
		
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
		//3. Print Title of the first course
		
		String firstCourseTitle = js.getString("course[0].title");
		System.out.println(firstCourseTitle);

		//4. Print All course titles and their respective Prices
		
		for(int i=0;i<courseCount;i++) {
			String title = js.getString("course["+i+"].title");
			System.out.println(title);
			
			System.out.println(js.get("course["+i+"].price").toString()); // 1st way to print the output(Use toString method)
			int price = js.getInt("course["+i+"].price");  // 2nd way to print the output
			System.out.println(price);
		}

		//5. Print no of copies sold by RPA Course
		
		for(int i=0;i<courseCount;i++) {
			String title = js.getString("course["+i+"].title");
			if(title.equalsIgnoreCase("RPA")) {
				System.out.println(js.get("course["+i+"].copies").toString()); //1st way
				int copies = js.getInt("course["+i+"].copies");  //2nd way
				System.out.println(copies);
				break;
			}
		}

		//6. Verify if Sum of all Course prices matches with Purchase Amount
		System.out.println("------------6. Verify if Sum of all Course prices matches with Purchase Amount");
		int sum=0;
		for(int i=0;i<courseCount;i++) {
			int price = js.getInt("course["+i+"].price");
			int copiesCount = js.getInt("course["+i+"].copies");
			int amount = price*copiesCount;
			System.out.println(amount);
			sum = sum + amount;
		}
		System.out.println(sum);
		int purchaseTotalAmount = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(sum, purchaseTotalAmount);
		

	}

}
