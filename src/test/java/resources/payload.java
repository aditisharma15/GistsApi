package resources;
public class payload {

	public static String postGistsPayload() {
	
     return "{\r\n" + 
				"  \"description\": \"Gists API Testing\",\r\n" + 
				"  \"public\": false,\r\n" + 
				"  \"files\": {\r\n" + 
				"    \"hello_world.rb\": {\r\n" + 
				"      \"content\": \"class HelloWorld\\n   def initialize(name)\\n      @name = name.capitalize\\n   end\\n   def sayHi\\n      puts \\\"Hello !\\\"\\n   end\\nend\\n\\nhello = HelloWorld.new(\\\"World\\\")\\nhello.sayHi\"\r\n" + 
				"    }\r\n" + 
				"  }\r\n" + 
				"}";
    	  
}
	public static String postGistsPayload_NoFile() {
		
	     return "\"description\": \"Gists API Testing\",\r\n" + 
	     		"  \"public\": false";
	    	  
	}
	
  public static String updateGistsPayload(String NewDescription) {
	  return "{\r\n" + 
				"  \"description\": \""+NewDescription+"\",\r\n" + 
				"  \"public\": false,\r\n" + 
				"  \"files\": {\r\n" + 
				"    \"hello_world.rb\": {\r\n" + 
				"      \"content\": \"class HelloWorld\\n   def initialize(name)\\n      @name = name.capitalize\\n   end\\n   def sayHi\\n      puts \\\"Hello !\\\"\\n   end\\nend\\n\\nhello = HelloWorld.new(\\\"World\\\")\\nhello.sayHi\"\r\n" + 
				"    }\r\n" + 
				"  }\r\n" + 
				"}";
  }
}