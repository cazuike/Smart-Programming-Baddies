package com.smartprogrammingbaddies.organization;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.smartprogrammingbaddies.client.Client;
import com.smartprogrammingbaddies.client.ClientRepository;
import com.smartprogrammingbaddies.event.Event;
import com.smartprogrammingbaddies.event.EventRepository;
import com.smartprogrammingbaddies.storagecenter.StorageCenter;
import com.smartprogrammingbaddies.storagecenter.StorageCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains the OrganizationController class.
 */
@RestController
public class OrganizationController {
  @Autowired
  private OrganizationRepository organizationRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private EventRepository eventRepository;

  @Autowired
  private StorageCenterRepository storageCenterRepository;

  private JsonObject json = new JsonObject();


  /**
   * Creates an organization with the given parameters.
   *
   * @param orgName the name of the Organization.
   * @param orgType the type of the Organization.
   * @param apiKey the API key of the client.
   * @return A {@code ResponseEntity} A message if the Organization was
   *      successfully created and a HTTP 200 response or,
   *      HTTP 500 reponse if an error occurred,
   *      or a HTTP 403 response if the API key is invalid.
  */
  @PostMapping("/createOrganization")
  public ResponseEntity<?> createOrganization(
       @RequestParam("orgName") String orgName,
       @RequestParam("orgType") String orgType,
       @RequestParam("apiKey") String apiKey) {
    try {
      Client client = clientRepository.findByApiKey(apiKey);
      if (client == null) {
        return new ResponseEntity<>("Invalid API Key", HttpStatus.FORBIDDEN);
      }
      Organization organization = new Organization(orgName, orgType, client);
      organization = organizationRepository.save(organization);
      return new ResponseEntity<>(organization, HttpStatus.OK);

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Retrieves the organization information.
   *
   * @param apiKey the API key of the client.
   * @param orgId  the id of the organization.
   * @return A {@code ResponseEntity} A message if the Organization was successfully created
   *     and a HTTP 200 response or, HTTP 500 reponse if an error occurred.
   */
  @GetMapping("/getOrganization")
  public ResponseEntity<?> getOrganization(
        @RequestParam("apiKey") String apiKey,
        @RequestParam("orgId") int orgId) {
    try {
      verifyApiKey(apiKey, orgId);
      Organization organization = organizationRepository.findById(orgId).orElseThrow();

      json = organization.toJson();

      boolean subscriptionStatus = organization.getSubscriptionStatus();
      Iterable<Event> events = eventRepository.findAll();

      if (subscriptionStatus) {
        JsonArray jsonEvents = new JsonArray();
        for (Event event : events) {
          jsonEvents.add(event.toString());
        }
        json.add("allEvents", jsonEvents);
      }

      return ResponseEntity.ok(json.toString());

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Changes an organization's subscription status to event notifications.
   *
   * @param apiKey the API key of the client.
   * @param orgId the id of the organization.
   * @return A {@code ResponseEntity} A message if the Organization was successfully found
   *     and status was update with a HTTP 200 response or, HTTP 500 reponse if an error occurred.
   */
  @PatchMapping("/changeSubscriptionStatus")
  public ResponseEntity<?> changeSubscriptionStatus(
          @RequestParam("apiKey") String apiKey,
          @RequestParam("orgId") int orgId) {
    try {
      verifyApiKey(apiKey, orgId);
      Organization organization = organizationRepository.findById(orgId).orElseThrow();
      boolean status = organization.getSubscriptionStatus();
      organization.changeSubscriptionStatus();
      organizationRepository.save(organization);

      if (status) {
        json.addProperty("subscriptionStatus", "false");
      } else {
        json.addProperty("subscriptionStatus", "true");
      }
      return new ResponseEntity<>(json.toString(), HttpStatus.OK);

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Deletes an organization.
   *
   * @param apiKey the API key of the client.
   * @param orgId  the id of the organization.
   * @return A {@code ResponseEntity} A message if the Organization was successfully deleted
   *     and a HTTP 200 response or, HTTP 500 reponse if an error occurred.
   */
  @DeleteMapping("/deleteOrganization")
  public ResponseEntity<?> deleteOrganization(
          @RequestParam("apiKey") String apiKey,
          @RequestParam("orgId") int orgId) {
    try {
      verifyApiKey(apiKey, orgId);
      Client client = clientRepository.findByApiKey(apiKey);
      organizationRepository.deleteById(orgId);
      client.setOrganization(null);
      clientRepository.save(client);
      json.addProperty("message", "Organization deleted successfully");
      return new ResponseEntity<>(json.toString(), HttpStatus.OK);

    } catch (NumberFormatException e) {
      return new ResponseEntity<>("Invalid Organization ID", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * register an event to an organization.
   *
   * @param apiKey the API key of the client.
   * @param orgId the id of the organization.
   * @param eventId the id of the event.
   * @return A {@code ResponseEntity} A message if the event was successfully registered
   *      and a HTTP 200 response or, HTTP 500 reponse if an error occurred.
   */
  @PostMapping("/registerEvent")
  public ResponseEntity<?> registerEvent(
          @RequestParam("apiKey") String apiKey,
          @RequestParam("orgId") int orgId,
          @RequestParam("eventId") int eventId) {
    try {
      verifyApiKey(apiKey, orgId);
      Organization organization = organizationRepository.findById(orgId).orElseThrow();
      Event event = eventRepository.findById(eventId).orElseThrow();
      if (event.getOrganizer() != null) {
        throw new IllegalArgumentException("Event already registered to an organization");
      }

      event.updateOrganizer(organization);
      eventRepository.save(event);
      organizationRepository.save(organization);
      json.addProperty("message", "Event registered successfully");
      return new ResponseEntity<>(json.toString(), HttpStatus.OK);

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Unregisters an event from an organization.
   *
   * @param apiKey the API key of the client.
   * @param orgId the id of the organization.
   * @param eventId the id of the event.
   * @return A {@code ResponseEntity} A message if the event was successfully unregistered
   *     and a HTTP 200 response or, HTTP 500 reponse if an error occurred.
   */
  @DeleteMapping("/unregisterEvent")
  public ResponseEntity<?> unregisterEvent(
          @RequestParam("apiKey") String apiKey,
          @RequestParam("orgId") int orgId,
          @RequestParam("eventId") int eventId) {
    try {
      verifyApiKey(apiKey, orgId);
      Event event = eventRepository.findById(eventId).orElseThrow();
      if (event.getOrganizer() == null) {
        throw new IllegalArgumentException("Event not registered to an organization");
      }

      event.updateOrganizer(null);
      eventRepository.save(event);
      Organization organization = organizationRepository.findById(orgId).orElseThrow();
      organizationRepository.save(organization);
      json.addProperty("message", "Event unregistered successfully");
      return new ResponseEntity<>(json.toString(), HttpStatus.OK);

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Lists all events registered to an organization.
   *
   * @param apiKey the API key of the client.
   * @param orgId the id of the organization.
   * @return A {@code ResponseEntity} A message if the events were successfully listed
   *     and a HTTP 200 response or, HTTP 500 reponse if an error occurred.
   */
  @GetMapping("/listOrganizationEvents")
  public ResponseEntity<?> listOrganizationEvents(
          @RequestParam("apiKey") String apiKey,
          @RequestParam("orgId") int orgId) {
    try {
      verifyApiKey(apiKey, orgId);
      Organization organization = organizationRepository.findById(orgId).orElseThrow();
      return new ResponseEntity<>(organization.getEvents(), HttpStatus.OK);

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Link a storage center to an organization.
   *
   * @param apiKey the API key of the client.
   * @param orgId the id of the organization.
   * @param storageId the id of the storage center.
   * @return A {@code ResponseEntity} A message if the storage center was successfully linked
   *     and a HTTP 200 response or, HTTP 500 reponse if an error occurred.
   */
  @PostMapping("/linkStorageCenter")
  public ResponseEntity<?> linkStorageCenter(
          @RequestParam("apiKey") String apiKey,
          @RequestParam("orgId") int orgId,
          @RequestParam("storageId") int storageId) {
    try {
      verifyApiKey(apiKey, orgId);
      Organization organization = organizationRepository.findById(orgId).orElseThrow();
      StorageCenter storage = storageCenterRepository.findById(storageId).orElseThrow();
      storage.setOrganization(organization);
      organization.setStorage(storage);
      organizationRepository.save(organization);
      json.addProperty("message", "Storage center linked successfully");
      return new ResponseEntity<>(json.toString(), HttpStatus.OK);

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Get the storage center id linked to an organization.
   *
   * @param apiKey the API key of the client.
   * @param orgId the id of the organization.
   * @return A {@code ResponseEntity} A message if the storage center was retrieved successfully
   *     and a HTTP 200 response or, HTTP 500 reponse if an error occurred.
   */
  @GetMapping("/getStorageCenter")
  public ResponseEntity<?> getStorageCenter(
          @RequestParam("apiKey") String apiKey,
          @RequestParam("orgId") int orgId) {
    try {
      verifyApiKey(apiKey, orgId);
      Organization organization = organizationRepository.findById(orgId).orElseThrow();
      StorageCenter storage = organization.getStorage();
      json.addProperty("id", storage.getDatabaseId());
      return new ResponseEntity<>(json.toString(), HttpStatus.OK);

    } catch (Exception e) {
      return handleException(e);
    }
  }



  /**
   * Handles exceptions that occur during the creation of an organization.

   * @param e the exception that occurred.
   * @return A {@code ResponseEntity} A message if the Organization was successfully created
   *     and a HTTP 200 response or, HTTP 500 reponse if an error occurred.
   */
  private ResponseEntity<?> handleException(Exception e) {
    JsonObject json = new JsonObject();
    json.addProperty("error", e.getMessage());
    boolean isBadRequest = e instanceof IllegalArgumentException;
    if (isBadRequest) {
      json.addProperty("error", e.getMessage());
      return ResponseEntity.badRequest().body(json.toString());
    }

    return new ResponseEntity<>(json.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private void verifyApiKey(String apiKey, int orgId) {
    Client client = clientRepository.findByApiKey(apiKey);
    if (client == null) {
      throw new IllegalArgumentException("Invalid API Key");
    }
    Organization clientOrganization = client.getOrganization();
    if (clientOrganization == null) {
      throw new IllegalArgumentException("Client is not associated with an organization");
    }

    if (clientOrganization.getDatabaseId() != orgId) {
      throw new IllegalArgumentException("Not authorized to access organization");
    }
  }
}