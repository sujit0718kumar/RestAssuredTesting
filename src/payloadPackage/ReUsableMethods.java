package payloadPackage;

import io.restassured.path.json.JsonPath;

public class ReUsableMethods {
	
	public static JsonPath convertRawToJson(String response) {
		JsonPath js = new JsonPath(response);
		return js;
	}

}
