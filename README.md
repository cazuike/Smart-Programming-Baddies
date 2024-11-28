# Smart-Programming-Baddies - Volunteer and Donation Coordinator

## Building and Running Local Instance
Please install the following to have this run locally:
  1. Maven 3.9.5: https://maven.apache.org/download.cgi Follow exact instructions as the site provides.
  2. JDK 17: This is what was used to implement the service, please make sure you use this JDK version as well: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
  3. Visual Studio Code: I used Visual Studio Code to implement the test cases and and run all command until deployment inclusive: https://code.visualstudio.com/download

4. Clone the Repository: This can be done from the startup of the VSCode window in the start display where it says clone Git Repository. You will be prompted to sign in into Github. Once signed in, copy and paste this url: https://github.com/cazuike/Smart-Programming-Baddies.git into the search box. Similar results are achieved in IntelliJ.

**<h3>IN ORDER TO RUN OUR PROGRAM LOCALLY, THE .env FILE IS NEEDED!</h3>**

The **.env** file that is used for compilation and that contains the secret keys needs to be under serviceSPB/
**<h3>PLEASE CONTACT US TO RECIEVE THIS FILE'S CREDENTIALS AND FORMAT IN ORDER TO COMPILE!</h3>**
bag2158@columbia.edu
eam2316@columbia.edu
ca2970@columbia.edu
jam2509@columbia.edu

5. Start up Local Instance: Navigate to the serviceSPB Directory. Once there, run the following command:
```
mvn spring-boot:run
```
6. Use Instance: The service can be used via a web browser or Postman with the index route of: http://127.0.0.1:8080/

All the endpoints are listed below, you can see all of their descriptions and what they are expected to do.

## Running Our Test
Tests are found under the serviceSPB/src/test directory.
Once all the above requirements have been met, you can run our test with the following commands:

To run the tests provided in this project, run the following command in the serviceSPB directory:
```
mvn test
```
Result will be under serviceSPB/target/surefire-reports

## Test Client Repository
A test Food Pantry app was created to do end-to-end testing for this service.
The repository is located here:
[Link](https://github.com/bryan-granda/food-pantry-client)

## Jira Board Link
Below is our team's project JIRA board:
[Link](https://donation-service.atlassian.net/jira/software/projects/SCRUM/boards/1)

## Tasks Not Completed:
We could not complete the Volunteer and Donations Logging that we proposed in our proposal

## Our GCloud Instance:
Our Deployed app can be found here:
[Link](https://team-project-439523.ue.r.appspot.com/)

### Run Jacoco Code Coverage Report
To run the Jacoco Code Coverage test, run the following command in the serviceSPB directory:
```
mvn jacoco:report
```
The results are located here: serviceSPB/target/site/jacoco/index.html. Open this HTML file in your preferred web browser to see the Jacoco report with a nice UI.

### Run style checker
To run the Maven style checker, run the following command in the serviceSPB directory:
```
mvn checkstyle:check
```
The result are located here: serviceSPB/target/checkstyle-result.xml

## Most Recent CI Reports
The CI reports can be found under:
[Link](https://github.com/cazuike/Smart-Programming-Baddies/actions)
Click on any of the workflow runs and the files will be located under Artifacts for 90 days since ran.

### PMD commands
Must have PMD installed.
Please refer to the PMD website to receive instructions on how to install PMD.
[link](https://pmd.github.io/)

1. Once installed, Nagivate to the src folder within the serviceSPB directory.

2. Run the following command:
```
mvn pmd:check
```

3. Results are located here: serviceSPB/target/pmd.xml.

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

**2. Add Volunteer to Event**
Associates a volunteer with an event.

URL: /addVolunteerToEvent
Method: POST
Query Parameters:
- apiKey (required): A valid API key for authentication.
- eventId (required): The ID of the event.
- volunteerId (required): The ID of the volunteer.

Response:
- Success (200): A message confirming the volunteer was added to the event.
- Error (404): A message indicating an invalid API key, event ID, or volunteer ID.
- Error (500): A message indicating an internal error occurred while adding the volunteer.


markdown
Copy code
## Event Controller

**1. Create Event**
Creates a new event and adds it to the system, associating it with a storage center and optional volunteers.

URL: /createEvent
Method: POST
Query Parameters:
- apiKey (required): A valid API key for authentication.
- name (required): The name of the event.
- description (required): A description of the event.
- date (required): The date of the event (YYYY-MM-DD format).
- startTime (required): The event's start time (HH:mm format).
- endTime (required): The event's end time (HH:mm format).
- location (required): The event location.
- storageCenterId (required): The ID of the associated storage center.
- organizationId (optional): The ID of the associated organization (currently unused).

Response:
- Success (200): A message with the event ID on successful creation.
- Error (404): A message indicating an invalid API key or storage center ID.
- Error (500): A message indicating an internal error occurred during event creation.

**2. Add Volunteer to Event**
Associates a volunteer with an event.

URL: /addVolunteerToEvent
Method: POST
Query Parameters:
- apiKey (required): A valid API key for authentication.
- eventId (required): The ID of the event.
- volunteerId (required): The ID of the volunteer.

Response:
- Success (200): A message confirming the volunteer was added to the event.
- Error (404): A message indicating an invalid API key, event ID, or volunteer ID.
- Error (500): A message indicating an internal error occurred while adding the volunteer.

**3. List All Events**
Fetches a list of all events in the system.

URL: /listEvents
Method: GET
Query Parameters:
- apiKey (required): A valid API key for authentication.

Response:
- Success (200): A list of all events.
- Error (404): A message indicating an invalid API key.
- Error (500): A message indicating an internal error occurred while fetching the events.

**4. Retrieve Event**
Fetches the details of a specific event.

URL: /retrieveEvent
Method: GET
Query Parameters:
- apiKey (required): A valid API key for authentication.
- eventId (required): The ID of the event to retrieve.

Response:
- Success (200): Event details.
- Error (404): A message indicating an invalid API key or event ID.
- Error (500): A message indicating an internal error occurred while fetching the event.

**5. Remove Event**
Deletes an event from the system.

URL: /removeEvent
Method: DELETE
Query Parameters:
- apiKey (required): A valid API key for authentication.
- eventId (required): The ID of the event to delete.

Response:
- Success (200): A confirmation message on successful deletion.
- Error (404): A message indicating an invalid API key or event ID.
- Error (500): A message indicating an internal error occurred during deletion.

**6. Search Events by Date**
Fetches a list of events on a specified date.

URL: /searchEventsByDate
Method: GET
Query Parameters:
- apiKey (required): A valid API key for authentication.
- date (required): The date to search for events (YYYY-MM-DD format).

Response:
- Success (200): A list of events on the specified date.
- Error (404): A message indicating an invalid API key or no events found.
- Error (500): A message indicating an internal error occurred while searching for events.

**7. Search Events by Location**
Fetches a list of events at a specified location.

URL: /searchEventsByLocation
Method: GET
Query Parameters:
- apiKey (required): A valid API key for authentication.
- location (required): The location to search for events.

Response:
- Success (200): A list of events at the specified location.
- Error (404): A message indicating an invalid API key or no events found.
- Error (500): A message indicating an internal error occurred while searching for events.

**8. Retrieve Event**
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

# Storage Center Endpoints

## 1. **Create a Storage Center**
URL: `/createCenter`
Method: `POST`
Query Parameters:
- `name` (required): A string representing the storage center's name.
- `description` (required): A string representing the storage center's description.

Response:
- `String` (200): The ID of the storage center if created successfully.
- `String` (500): An error message if an internal server error occurred.
- `String` (400): An error message if any parameter is incorrectly formatted.

---

## 2. **Retrieves the Storage Center Information**
URL:   `/getCenterInfo`
Method: `GET`
Query Parameters:
- `storageCenterId` (required): An integer representing the storage center's ID.

Response:
- `StorageCenter` (200): The details of the storage center.
- `String` (500): An error message if an internal server error occurred.
- `String` (404): An error message if the storage center ID was not found.

---

## 3. **Deletes a Storage Center from the database**
URL:   `/deleteCenter`
Method: `DELETE`
Query Parameters:
- `storageCenterId` (required): An integer representing the storage center's ID.

Response:
- `String` (200): A message confirming the storage center was successfully deleted.
- `String` (500): An error message if an internal server error occurred.
- `String` (404): An error message if the storage center ID was not found.

---

## 4. **Updates the Storage Center's name**
URL:   `/updateCenterName`
Method: `PATCH`
Query Parameters:
- `storageCenterId` (required): An integer representing the storage center's ID.
- `name` (required): A string representing the new name of the storage center.

Response:
- `String` (200): A message confirming the storage center's name was updated.
- `String` (500): An error message if an internal server error occurred.
- `String` (400): An error message if any parameter is incorrectly formatted.
- `String` (404): An error message if the storage center ID was not found.

---

## 5. **Updates the Storage Center's description**
URL:   `/updateCenterDescription`
Method: `PATCH`
Query Parameters:
- `storageCenterId` (required): An integer representing the storage center's ID.
- `description` (required): A string representing the new description of the storage center.

Response:
- `String` (200): A message confirming the storage center's description was updated.
- `String` (500): An error message if an internal server error occurred.
- `String` (400): An error message if any parameter is incorrectly formatted.
- `String` (404): An error message if the storage center ID was not found.

---

## 6. **Updates the Storage Center's operating hours**
URL:   `/updateCenterHours`
Method: `PATCH`
Query Parameters:
- `storageCenterId` (required): An integer representing the storage center's ID.
- `day` (required): An integer representing the day of the week (1–7).
- `open` (required): A string representing the opening time in `HH:MM` format.
- `close` (required): A string representing the closing time in `HH:MM` format.

Response:
- `String` (200): A message confirming the storage center's operating hours were updated.
- `String` (500): An error message if an internal server error occurred.
- `String` (400): An error message if any parameter is incorrectly formatted.
- `String` (404): An error message if the storage center ID was not found.

---

## 7. **Adds an item to the Storage Center**
URL:   `/checkInItems`
Method: `PATCH`
Query Parameters:
- `storageCenterId` (required): An integer representing the storage center's ID.
- `type` (required): A string representing the item type (FOOD, CLOTHES, or TOILETRIES).
- `name` (required): A string representing the item's name.
- `quantity` (required): An integer representing the item's quantity (positive value).
- `expirationDate` (required): A string representing the item's expiration date in `yyyy-MM-dd` format.

Response:
- `String` (200): A message confirming the item was successfully added.
- `String` (500): An error message if an internal server error occurred.
- `String` (400): An error message if any parameter is incorrectly formatted.
- `String` (404): An error message if the storage center ID was not found.

---

## 8. **Removes an item from the Storage Center**
URL:   `/checkOutItems`
Method: `PATCH`
Query Parameters:
- `storageCenterId` (required): An integer representing the storage center's ID.
- `type` (required): A string representing the item type (FOOD, CLOTHES, or TOILETRIES).
- `name` (required): A string representing the item's name.
- `quantity` (required): An integer representing the item's quantity.

Response:
- `String` (200): A message confirming the item was successfully removed.
- `String` (500): An error message if an internal server error occurred.
- `String` (400): An error message if any parameter is incorrectly formatted.
- `String` (404): An error message if the storage center ID was not found.

---

## 9. **Removes expired items from inventory**
URL: `/removeExpiredItems`
Method: `PATCH`
Query Parameters:
- `storageCenterId` (required): An integer representing the storage center's ID.

Response:
- `String` (200): A message confirming the expired items were successfully removed.
- `String` (500): An error message if an internal server error occurred.
- `String` (404): An error message if the storage center ID was not found.

---

## 10. **Get the Storage Center's inventory**
URL: `/listInventory`
Method: `GET`
Query Parameters:
- `storageCenterId` (required): An integer representing the storage center's ID.

Response:
- `List<Item>` (200): A list of items in the storage center's inventory.
- `String` (500): An error message if an internal server error occurred.
- `String` (404): An error message if the storage center ID was not found.

---

## 11. **Get the Storage Center's transactions**
URL: `/listTransactions`
Method: `GET`
Query Parameters:
- `storageCenterId` (required): An integer representing the storage center's ID.

Response:
- `List<Transaction>` (200): A list of transactions from the storage center.
- `String` (500): An error message if an internal server error occurred.
- `String` (404): An error message if the storage center ID was not found.

# Client Endpoint

## 1. **Register A Client**
URL: `/registerClient`
Method: `POST`
Response:
- `API Key` (200): With the new API Key.
- `API Key` (500): If the API key could not be generated.

# Organization Endpoints

## 1. **Retrieve Organization Information**
URL: `/getOrganization`
Method: `GET`
Query Parameters:
- `apiKey` (required): A string representing the client's API key.
- `orgId` (required): An integer representing the organization's ID.

Response:
- `Organization` (200): The organization's details if found successfully.
- `String` (500): An error message if an internal server error occurred.
- `String` (400): An error message if the organization was not found.

---

## 2. **Create Organization**
URL: `/createOrganization`
Method: `GET`
Query Parameters:
- `orgName` (required): the name of the Organization.
- `orgType` (required): the type of the Organization.
- `orgId` (required): An integer representing the organization's ID.

Response:
- `Organization` (200): The organization's details if found successfully.
- `String` (500): An error message if an internal server error occurred.
- `String` (403): If the API key is invalid.

---

## 3. **Change Organization Subscription Status**
URL: `/changeSubscriptionStatus`
Method: `PATCH`
Query Parameters:
- `apiKey` (required): A string representing the client's API key.
- `orgId` (required): An integer representing the organization's ID.

Response:
- `String` (200): A success message if the subscription status was updated.
- `String` (500): An error message if an internal server error occurred.

---

## 4. **Delete Organization**
URL: `/deleteOrganization`
Method: `DELETE`
Query Parameters:
- `apiKey` (required): A string representing the client's API key.
- `orgId` (required): An integer representing the organization's ID.

Response:
- `String` (200): A success message if the organization was deleted.
- `String` (500): An error message if an internal server error occurred.

---

## 5. **Register an Event to an Organization**
URL: `/registerEvent`
Method: `POST`
Query Parameters:
- `apiKey` (required): A string representing the client's API key.
- `orgId` (required): An integer representing the organization's ID.
- `eventId` (required): An integer representing the event's ID.

Response:
- `String` (200): A success message if the event was registered.
- `String` (500): An error message if an internal server error occurred.

---

## 6. **Unregister an Event from an Organization**
URL: `/unregisterEvent`
Method: `DELETE`
Query Parameters:
- `apiKey` (required): A string representing the client's API key.
- `orgId` (required): An integer representing the organization's ID.
- `eventId` (required): An integer representing the event's ID.

Response:
- `String` (200): A success message if the event was unregistered.
- `String` (500): An error message if an internal server error occurred.

---

## 7. **List All Events Registered to an Organization**
URL: `/listOrganizationEvents`
Method: `GET`
Query Parameters:
- `apiKey` (required): A string representing the client's API key.
- `orgId` (required): An integer representing the organization's ID.

Response:
- `List<Event>` (200): A list of events registered to the organization.
- `String` (500): An error message if an internal server error occurred.

---

## 8. **Link a Storage Center to an Organization**
URL: `/linkStorageCenter`
Method: `POST`
Query Parameters:
- `apiKey` (required): A string representing the client's API key.
- `orgId` (required): An integer representing the organization's ID.
- `storageId` (required): An integer representing the storage center's ID.

Response:
- `String` (200): A success message if the storage center was linked.
- `String` (500): An error message if an internal server error occurred.

---

## 9. **Get the Storage Center Linked to an Organization**
URL: `/getStorageCenter`
Method: `GET`
Query Parameters:
- `apiKey` (required): A string representing the client's API key.
- `orgId` (required): An integer representing the organization's ID.

Response:
- `StorageCenter` (200): The details of the linked storage center.
- `String` (500): An error message if an internal server error occurred.



# Tools Utilized 🔬
- Spring Data JPA
- Google Cloud SQL
- Maven Package Manager
- GitHub Actions CI
- Checkstyle
- PMD Static Bug Analysis




