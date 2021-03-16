package PlayersAPI.RestAssured;

import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;
import java.util.HashMap;


public class PlayersApiTests {
	
	//Verification of get operation. Lists all the records.
		@Test(priority=1)
		public void testGetAll() {
			given()
			
			.when()
				.get("http://localhost:8085/player")
			.then()
				.statusCode(200);
			
		}
		
			//Verification of  post operation. Add a new record.
			@Test(priority=2)
			public void testAddNew() {
		
			HashMap<String,Object> data = new HashMap<String,Object>();
			data.put("firstName", "Harshi");
			data.put("lastName", "vijay");
			data.put("age", 36);
			data.put("gender", "Female");
			data.put("jersey", 58);
			
			Response res=
				given()
					.contentType(ContentType.JSON)
					.body(data)
				
				.when()
					.post("http://localhost:8085/player")
				.then()
					.statusCode(200)
					.log().body()
					.extract().response();
				
				String jsonString=res.asString();
				assertEquals(jsonString.contains("Player added!"), true);
			}
		
		//Verification of  get operation by id. 
		@Test(priority=3)
		public void testGetById() {
	        given()
	        
	        .when()
				.get("http://localhost:8085/player/5")
			.then()
				.statusCode(200)
				.log().body()
				.body("age",equalTo(36))
				.body("firstName", equalTo("Harshi"));
		}
		
		//Verification of error message for adding duplicate data. 
		@Test(priority=4)
		public void testNegativeAddNew() {
	
		HashMap<String,Object> data = new HashMap<String,Object>();
		data.put("firstName", "Harshi");
		data.put("lastName", "vijay");
		data.put("age", 36);
		data.put("gender", "Female");
		data.put("jersey", 58);
		
		Response res=
			given()
				.contentType(ContentType.JSON)
				.body(data)
			
			.when()
				.post("http://localhost:8085/player")
			.then()
				.statusCode(200)
				.log().body()
				.extract().response();
			
			String jsonString=res.asString();
			assertEquals(jsonString.contains("Error! Jersey number already exists!"), true);
		}
		
		
		//Verification of put operation by id. Update a new record.
		@Test(priority=5)
		public void testUpdateById() {
			
			HashMap<String,Object> data = new HashMap<String,Object>();
		    data.put("firstName", "Harshitha");
			data.put("lastName", "vijay");
			data.put("age", 67);
			data.put("gender", "Female");
			data.put("jersey", 58);
			
			Response res=
			given()
			.contentType(ContentType.JSON)
			.body(data)
			
		
		.when()
			.put("http://localhost:8085/player/5")
		.then()
				.statusCode(200)
			.log().body()
				.extract().response();
			
			String jsonString=res.asString();
			assertEquals(jsonString.contains("Player updated!"), true);
		}
		
		//Verification of error message for put operation for an id that does not exist. 
				@Test(priority=6)
				public void testNegativeUpdateById() {
					
					HashMap<String,Object> data = new HashMap<String,Object>();
				    data.put("firstName", "Harshi");
					data.put("lastName", "arun");
					data.put("age", 37);
					data.put("gender", "Female");
					data.put("jersey", 28);
					
					Response res=
					given()
					.contentType(ContentType.JSON)
					.body(data)
					
				
				.when()
					.put("http://localhost:8085/player/10")
				.then()
						.statusCode(200)
					.log().body()
						.extract().response();
					
					String jsonString=res.asString();
					assertEquals(jsonString.contains("Player does not exist!"), true);
				}
		
		//Verification of delete operation by id. Delete a new record.
		@Test(priority=7)
		public void testDeleteById() {
			
			Response res=
	        given()
			
			.when()
				.delete("http://localhost:8085/player/5")
			.then()
				.statusCode(200)
				.log().body()
				.extract().response();
			
			String jsonString=res.asString();
			assertEquals(jsonString.contains("Player deleted"), true);
		}
		
		//Verification of error message for delete operation for a record that does not exist. 
		@Test(priority=8)
		public void testNegativeDeleteById() {
			
			Response res=
	        given()
			
			.when()
				.delete("http://localhost:8085/player/5")
			.then()
				.statusCode(200)
				.log().body()
				.extract().response();
			
			String jsonString=res.asString();
			assertEquals(jsonString.contains("Player does not exist!"), true);
		}

}
