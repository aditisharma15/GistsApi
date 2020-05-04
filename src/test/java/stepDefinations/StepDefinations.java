package stepDefinations;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.payload;
import resources.utils;

//In utlis class , i have build a request specification method which contains the base Uri and headers which are common to each Api call . 
//So that it doesnt require to build them again and again
//And extended the Utils class so that its method can be called directly without creating object for the class.


public class StepDefinations extends utils{
	
	// these variables are declared as instance variable so that there is no need to declare them again and again for each method
	RequestSpecification request;
	ResponseSpecification respec;
    Response response;
    String gistsId;
    String NewDescription = "Hello World Examples updated";
    
    //Request is build here for post Gists API
    // Body for the post request is taken from payload class under resources package
	@Given("Add Post gists payload")
	public void add_Post_gists_payload() throws FileNotFoundException {
	request = given().log().all().spec(requestSpecification()).body(payload.postGistsPayload());
	respec = new ResponseSpecBuilder().expectStatusCode(201).build();

	}
	
	
    //Post Gists Api  is called and its response is extracted in a String Postresponse
	@When("{string} Api is called")
	public void api_is_called(String string) {
	response =request.when().post("gists").then().spec(respec).extract().response();
	String Postresponse=response.asString();
	System.out.println("Postresponse ="+ Postresponse);
	JsonPath js = new JsonPath(Postresponse);
    gistsId=js.getString("id"); // GistId is extracted from response to be used further
 
	}
    
	
	// Validation for status code 201  for post gists Api
	@Then("the gists is successfully added when status code is {int}")
	public void the_gists_is_successfully_added_when_status_code_is(Integer int1) {
    assertEquals(response.statusCode(),201);
	}
	
	
	//Get gists APi is called and response is stored in String 
	// GistsId in request is taken from postGists Api response
	@When("Get gists Api is called")
	public void get_gists_Api_is_called() throws FileNotFoundException {
	request = given().log().all().spec(requestSpecification());
	respec = new ResponseSpecBuilder().expectStatusCode(200).build();
	response =request.when().log().all().get("gists"+"/" + ""+gistsId+"").then().spec(respec).extract().response();
	String GetresponseValid=response.asString();
	System.out.println("GetresponseValid="+GetresponseValid);
	}

	//Validation for status code 200 for get gists api call
	@Then("verify gists is successfully retrieved when status code is {int}")
	public void the_gists_is_successfully_retrieved_when_status_code_is(Integer int1) {
	assertEquals(response.statusCode(),200);
	}
	
	//Post gists Api request is build without body to validate negative scenario
	@Given("Add Post gists payload without file")
	public void add_Post_gists_payload_without_file() throws FileNotFoundException {
	request = given().log().all().spec(requestSpecification());
	respec = new ResponseSpecBuilder().expectStatusCode(422).build();
	}

	
	//Validate status code 422 when post gist Api is called without giving the body in the post request
	@Then("verify gists is not added when status code is {int}")
	public void verify_gists_is_not_added_when_status_code_is(Integer int1) {
	assertEquals(response.statusCode(),422);
	String PostresponseInvalid=response.asString();
	System.out.println("PostresponseInvalid="+PostresponseInvalid);
	}
	
	//Get gists Api is called with in valid gistsId in the request and and response is stored in the string

		@When("Get gists Api with invalid gistsId is called")
		public void get_gists_Api_with_invalid_gistsId_is_called() throws FileNotFoundException {
		request = given().log().all().spec(requestSpecification());
		respec = new ResponseSpecBuilder().expectStatusCode(404).build();
		response =request.when().log().all().get("gists"+"/cnslncoasnx").then().spec(respec).extract().response();
		String GetresponseInValid=response.asString();
		System.out.println("GetresponseValid="+GetresponseInValid);
		}

		
		// verify status code 404 and error message
		@Then("verify error response")
		public void verify_error_response() {
		assertEquals(response.statusCode(),404);
		response.then().body( "message", equalTo("Not Found"));
		}
		
		// Update gists Api is called and response is stored in string
		@When("Update gists Api with valid gistsId is called")
		public void update_gists_Api_with_valid_gistsId_is_called() throws FileNotFoundException {
		request = given().log().all().spec(requestSpecification().body(payload.updateGistsPayload(NewDescription)));
		respec = new ResponseSpecBuilder().expectStatusCode(200).build();
		response =request.when().log().all().patch("gists"+"/" + ""+gistsId+"").then().spec(respec).extract().response();
		String UpdateresponseValid=response.asString();
		System.out.println("UpdateresponseValid="+UpdateresponseValid);
		assertEquals(response.statusCode(),200);
		}

		//Validation to check if the decription is updated successfully when it was update Api was called
		@Then("Verify gists is updated")
		public void verify_gists_is_updated() {
		response.then().assertThat().body("description", equalTo(NewDescription));

}
		
		//Update gists APi with invalid GistsId is called and response is stored in string
		@When("Update gists Api with invalid gistsId is called")
		public void update_gists_Api_with_invalid_gistsId_is_called() throws FileNotFoundException {
			request = given().log().all().spec(requestSpecification().body(payload.updateGistsPayload(NewDescription)));
			respec = new ResponseSpecBuilder().expectStatusCode(404).build();
			response =request.when().log().all().patch("gists"+"/bcdjsbcksbc").then().spec(respec).extract().response();
			String UpdateresponseInValid=response.asString();
			System.out.println("UpdateresponseValid="+UpdateresponseInValid);
			
		}


		// Delete Api is called and gistsId is taken for post Gist request
		@When("delete gists Api with valid gistsId is called")
		public void delete_gists_Api_with_valid_gistsId_is_called() throws FileNotFoundException {
			request = given().log().all().spec(requestSpecification());
			respec = new ResponseSpecBuilder().expectStatusCode(204).build();
			response =request.when().log().all().delete("gists"+"/" + ""+gistsId+"").then().spec(respec).extract().response();
			String DeleteresponseValid=response.asString();
			System.out.println("DeleteresponseValid="+DeleteresponseValid);
		}

		
		// delete Api with invalid Gists Api is called
		@When("delete gists Api with invalid gistsId is called")
		public void delete_gists_Api_with_invalid_gistsId_is_called() throws FileNotFoundException {
			request = given().log().all().spec(requestSpecification());
			respec = new ResponseSpecBuilder().expectStatusCode(404).build();
			response =request.when().log().all().delete("gists"+"vjbknlmlj").then().spec(respec).extract().response();
			String DeleteresponseInValid=response.asString();
			System.out.println("Deleteresponse with InValid GistId="+DeleteresponseInValid);
		}
		
		
		//Validate response code 204 for delete Api
		@Then("verify response code {int} for delete Api")
		public void verify_response_code_for_delete_Api(Integer int1) {
			assertEquals(response.statusCode(),204);
		}

		
		// Validate is already deleted APi is called with get gists request ,404 status code should return
		@Then("Get gists Api with deleted gistsId is called")
		public void get_gists_Api_with_deleted_gistsId_is_called() throws FileNotFoundException {
			request = given().log().all().spec(requestSpecification());
			respec = new ResponseSpecBuilder().expectStatusCode(404).build();
			response =request.when().log().all().get("gists"+"/" + ""+gistsId+"").then().spec(respec).extract().response();
			String GetresponseValid=response.asString();
			System.out.println("Response for Get Gists Api when gistId is deleted="+GetresponseValid);
		}
}



