Feature: Validate Gists API

Scenario: Verify if gists is successfully added using POST gists API
Given Add Post gists payload
When "Post gists" Api is called
Then the gists is successfully added when status code is 201

Scenario: Verify if gists is not added when POST gists API is called withoiut giving file
Given Add Post gists payload without file
When "Post gists" Api is called
Then verify gists is not added when status code is 422

Scenario: Verify if gists is retrieved successfully using GET gists API
Given Add Post gists payload
And "Post gists" Api is called
When Get gists Api is called
Then verify gists is successfully retrieved when status code is 200


Scenario: Verify error if wrong gistsId is goven in the Get gists request
Given Add Post gists payload
And "Post gists" Api is called
When Get gists Api with invalid gistsId is called
Then verify error response 

Scenario: Verify if gists is updated successfully
Given Add Post gists payload
And "Post gists" Api is called
When Update gists Api with valid gistsId is called
Then verify gists is successfully retrieved when status code is 200
And Verify gists is updated

Scenario: Verify error is wrong gistsId is given in update gists api
Given Add Post gists payload
And "Post gists" Api is called
When Update gists Api with invalid gistsId is called
Then verify error response

Scenario: Verify gists is deleted successfully
Given Add Post gists payload
And "Post gists" Api is called
When delete gists Api with valid gistsId is called
Then verify response code 204 for delete Api
And Get gists Api with deleted gistsId is called
And verify error response


Scenario: Verify error if wrong gist id is given in the delete gist Api request
Given Add Post gists payload
And "Post gists" Api is called
When delete gists Api with invalid gistsId is called
Then verify error response
