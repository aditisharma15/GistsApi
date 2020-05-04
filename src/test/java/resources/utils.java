package resources;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

public class utils{
	RequestSpecification req;
	
	public RequestSpecification requestSpecification() throws FileNotFoundException {
		
		PrintStream logFile = new PrintStream(new FileOutputStream("log.txt"));
		RestAssured.baseURI="https://api.github.com";	
		req=new RequestSpecBuilder().setBaseUri("https://api.github.com").addHeader("Authorization","Bearer 37d98e106245b029027e0ed7eb7018cab674915b").
				addHeader("Content-Type","application/json").
				addFilter(RequestLoggingFilter.logRequestTo(logFile))
				.addFilter(ResponseLoggingFilter.logResponseTo(logFile)).
				build();
		return req;
		
	}

}
