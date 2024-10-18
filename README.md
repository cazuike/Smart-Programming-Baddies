# Smart-Programming-Baddies - Volunteer and Donation Coordinator

## Building and Running Local Instance
Please install the following to have this run locally:
  1. Maven 3.9.5: https://maven.apache.org/download.cgi Follow exact instructions as the site provides.
  2. JDK 17: This is what was used to implement the service, please make sure you use this JDK version as well: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
  3. Visual Studio Code: I used Visual Studio Code to implement the test cases and and run all command until deployment inclusive: https://code.visualstudio.com/download


All the endpoints are listed on the RouteController.java file, you can see all of their descriptions and what they are expected to do. 

## Running Our Test
Once all the above requirements have been met, you can run our test with the following commands: 
To check whether an endpoint is working correctly, please feel free to edit the tests on the RouteControllerTests.java file. 
When satisified run: **mvn test** or **mvn test**. To check for style **mvn checkstyle:check**

All tests on this file are expected to test whether the endpoints succeed or fail. Upon testing you'll find that no test are failing, this is due to code working as expected. 

Once you run the command you'll see a report with all tests and their status on the command line interface or which ever one you decided to use. 

# Endpoints 🛜

## AuthController
**1. Generate API Key**
Generates a new, unique API key for the client to use in requests to other services.

URL: /generateApiKey
Method: GET
Response:
Success (200): A message containing the generated API key.
Error (500): A message indicating the API key could not be generated.

**2. Verify API Key**
Verifies if the provided API key exists in the database and is valid for use.

URL: /verifyApiKey
Method: GET
Query Parameters:
apiKey (required): The API key to verify.
Response:
Success (200): A message indicating the API key is valid.
Error (404): A message indicating the API key is not found.

**3. Remove API Key**
Removes the specified API key from the database.

URL: /removeApiKey
Method: DELETE
Query Parameters:
apiKey (required): The API key to remove.
Response:
Success (200): A message indicating the API key was removed.
Error (500): A message indicating the API key could not be removed.

**Error Handling**
**500 Internal Server Error:** This status is returned when there is a failure during the API key generation or removal process.
**404 Not Found:** This status is returned when an API key could not be found in the database during verification.

## Route Controller
**1. Homepage**
Redirects users to the homepage of the system, providing a welcome message.

URL: /, /index, /home
Method: GET
Response:
Success (200): Returns a welcome message indicating successful redirection to the homepage.

## Volunteer Controller
**1. Enroll Volunteer**
Enrolls a volunteer into the system, associating the volunteer with the client identified by the provided API key.

URL: /enrollVolunteer
Method: POST
Query Parameters:
apiKey (required): A valid API key provided by the client.
name (required): The name of the volunteer being enrolled.
Response:
Success (200): A message containing the enrolled volunteer's ID.
Error (404): A message indicating an invalid API key.
Error (500): A message indicating an internal error occurred during enrollment.

**2. Remove Volunteer**
Removes a volunteer from the system based on the provided volunteer ID and the associated API key.

URL: /removeVolunteer
Method: DELETE
Query Parameters:
apiKey (required): A valid API key provided by the client.
volunteerId (required): The unique ID of the volunteer to be removed.
Response:
Success (200): A message indicating the volunteer was successfully removed.
Error (404): A message indicating an invalid API key.
Error (500): A message indicating an internal error occurred during the removal process.



# Tools Utilized 🔬
- Firebase DB
- Maven Package Manager
- GitHub Actions CI
- Checkstyle




