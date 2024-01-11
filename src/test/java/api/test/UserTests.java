package api.test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints2;
import api.payload.User;

public class UserTests {
	Faker faker;
	User userPayload;
	
	public Logger logger;
	
	@BeforeClass
	public void setup() {
	
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//for logs
		logger = LogManager.getLogger(this.getClass());
		
}

	@Test (priority = 1)
	public void testPostUser() {
		logger.info(" --- Creating user --- "); //can add as many as needed
		Response response = UserEndPoints.createUser(userPayload);
		response.then().log().all();
		assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test (priority = 2)
	public void testGetUserByName() {
		Response response = UserEndPoints.readUser(this.userPayload.getUsername());
		response.then().log().all();
		assertEquals(response.getStatusCode(),200);
	}
	
	@Test (priority = 3)
	public void testUpdateByUsername() { //getting urls from properties file serEndPoints2
		//update data using payload 
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndPoints2.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().all();
		assertEquals(response.getStatusCode(), 200);
		//response.then().statusCode(200); same as above
		
		//checking updated data
		
		Response responseAfterUpdate = UserEndPoints.readUser(this.userPayload.getUsername());
		responseAfterUpdate.then().log().all();
		assertEquals(responseAfterUpdate.getStatusCode(),200);
			
	}
	
	@Test (priority = 4)
	public void testDeleteUserByName() { //getting urls from properties file serEndPoints2
		Response response = UserEndPoints2.deleteUser(this.userPayload.getUsername());
		assertEquals(response.getStatusCode(),200);
	}
	
}	
	
	
	
	
	
	
