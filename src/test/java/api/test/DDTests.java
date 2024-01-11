package api.test;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {

	@Test (priority = 1, dataProvider = "Data", dataProviderClass=DataProviders.class) //specify which data provider method
	public void testPostUser(String userID, String userName, String fname, String lname, String usermail, String password, String phone) {
	User userPayload = new User();
	
	//we create pojo using data from the sheet
	userPayload.setId(Integer.parseInt(userID));
	userPayload.setUsername(userName);
	userPayload.setFirstName(fname);
	userPayload.setLastName(lname);
	userPayload.setEmail(usermail);
	userPayload.setPassword(password);
	userPayload.setPhone(phone);
		
	Response response = UserEndPoints.createUser(userPayload);
	assertEquals(response.getStatusCode(),200);
	}
	
	@Test (priority = 2, dataProvider = "UserNames", dataProviderClass=DataProviders.class)
	public void deleteUserByName(String username) {
		Response response = UserEndPoints.deleteUser(username);
		assertEquals(response.getStatusCode(),200);
		
	}
}
