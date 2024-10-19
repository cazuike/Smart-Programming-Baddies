# Smart-Programming-Baddies - Volunteer and Donation Coordinator

## Building and Running Local Instance
Please install the following to have this run locally:
  1. Maven 3.9.5: https://maven.apache.org/download.cgi Follow exact instructions as the site provides.
  2. JDK 17: This is what was used to implement the service, please make sure you use this JDK version as well: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
  3. Visual Studio Code: I used Visual Studio Code to implement the test cases and and run all command until deployment inclusive: https://code.visualstudio.com/download


All the endpoints are listed in our *Controller.java file, you can see all of their descriptions and what they are expected to do.

## Running Our Test
Once all the above requirements have been met, you can run our test with the following commands:
To check whether an endpoint is working correctly, please feel free to edit the tests on the RouteControllerTests.java file.
When satisified run: **mvn test** or **mvn test**. To check for style **mvn checkstyle:check**

All tests on this file are expected to test whether the endpoints succeed or fail. Upon testing you'll find that no test are failing, this is due to code working as expected.

Once you run the command you'll see a report with all tests and their status on the command line interface or which ever one you decided to use.

**Note that you need our sensitive firebase_DB JSON in order to properly perform local tests, however it is not needed for cloud testing. If interested, please contact us.**

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

**2. Register Location**
Register a new location where donations can be held or stored. 

URL: /registerLocation
Method: PATCH
Query Parameters:
apiKey (required): A valid API key provided by the client.
location (required): The location being added.
Response:
Success (200): A message indicating successful registration of a new location as storage.
Error (404): A message indicating an invalid API key.
Error (500): A message indicating an internal error occurred during enrollment.

**3. Add Donation**
Add a donation to an existing storage center. If the location is not an existing storage center, a new reference will be created. 

URL: /addDonation
Method: PATCH
Query Parameters:
apiKey (required): A valid API key provided by the client.
location (required): The location being added.
donationName (required): The name of donation.
donationType (required): The type of donation.
donator (required): donator.
Response:
Success (200): A message indicating successful addition of new donation to a storage location.
Error (404): A message indicating an invalid API key.
Error (500): A message indicating an internal error occurred during enrollment.

**4. List Locations**
Lists all existing locations in the system, able to receive donations.

URL: /listLocations
Method: Get
Query Parameters:
apiKey (required): A valid API key provided by the client.
Response:
Success (200): A message listing all the existing locations within the database. 
Error (404): A message indicating an invalid API key.
Error (500): A message indicating an internal error occurred during enrollment.

## Volunteer Controller
**1. Enroll Volunteer**
Enrolls a volunteer into the system, associating the volunteer with the client identified by the provided API key.

URL: /enrollVolunteer
Method: PATCH
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

**3. Update Name**
Update name of an existing volunteer. 

URL: /updateName
Method: PATCH
Query Parameters:
apiKey (required): A valid API key provided by the client.
volunteerId (required): The unique ID of the volunteer.
Response:
Success (200): A message indicating the volunteer name was successfully updated.
Error (404): A message indicating an invalid API key.
Error (500): A message indicating an internal error occurred during the removal process.

**4. Update Schedule**
Update schedule of an existing volunteer. 

URL: /updateSchedule
Method: PATCH
Query Parameters:
apiKey (required): A valid API key provided by the client.
volunteerId (required): The unique ID of the volunteer.
Response:
Success (200): A message indicating the volunteer schedule was successfully updated.
Error (404): A message indicating an invalid API key.
Error (500): A message indicating an internal error occurred during the removal process.

**3. Update Role**
Update role of an existing volunteer. 

URL: /updateRole
Method: PATCH
Query Parameters:
apiKey (required): A valid API key provided by the client.
volunteerId (required): The unique ID of the volunteer.
Response:
Success (200): A message indicating the volunteer role was successfully updated.
Error (404): A message indicating an invalid API key.
Error (500): A message indicating an internal error occurred during the removal process.

**5. RetrieveVolunteer**
Retrieve pertinent information about a volunteer such as their schedule, name, role. 

URL: /retrieveVolunteer
Method: GET
Query Parameters:
apiKey (required): A valid API key provided by the client.
volunteerId (required): The unique ID of the volunteer.
Response:
Success (200): A string message with the information of the volunteer.
Error (404): A message indicating an invalid API key.
Error (500): A message indicating an internal error occurred during the removal process.

## Event Controller
**1. Create Event**
Creates a new event in the system, associating the event with the client identified by the provided API key.

URL: /createEvent
Method: POST
Query Parameters:
- apiKey (required): A valid API key provided by the client.
- name (required): The name of the event being created.
- description (required): The description of the event.
- date (required): The date of the event.
- time (required): The time of the event.
- location (required): The location of the event.
- organizer (required): The organizer of the event.

Response:
- Success (200): A message containing the created event's ID.
- Error (404): A message indicating an invalid API key.
- Error (500): A message indicating an internal error occurred during event creation.

**2. Retrieve Event**
Retrieves information of a specified event given by the event ID and the associated API key.

URL: /retrieveEvent
Method: GET
Query Parameters:
- apiKey (required): A valid API key provided by the client.
- eventId (required): The unique ID of the event to be retrieved.

Response:
- Success (200): A message containing the event information.
- Error (404): A message indicating an invalid API key.
- Error (500): A message indicating an internal error occurred during event retrieval.

**3. Remove Event**
Removes an event from database based on the provided event ID and the associated API key.

URL: /removeEvent
Method: DELETE
Query Parameters:
- apiKey (required): A valid API key provided by the client.
- eventId (required): The unique ID of the event to be removed.

Response:
- Success (200): A message indicating the event was successfully removed.
- Error (404): A message indicating an invalid API key.
- Error (500): A message indicating an internal error occurred during the removal process.

# Tools Utilized 🔬
- Firebase DB
- Maven Package Manager
- GitHub Actions CI
- Checkstyle




