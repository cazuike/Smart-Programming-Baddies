package com.smartprogrammingbaddies.storagecenter;

import com.smartprogrammingbaddies.item.Item;
import com.smartprogrammingbaddies.item.ItemId;
import com.smartprogrammingbaddies.item.ItemRepository;
import com.smartprogrammingbaddies.utils.TimeSlot;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The StorageCenterController API Class used to manage
 * the storage centers in the database.
 */
@RestController
public class StorageCenterController {
  @Autowired
  StorageCenterRepository storageCenterRepository;
  @Autowired
  ItemRepository itemRepository;

  /**
   * Enrolls a storage center into the database.
   *
   * @param name A {@code String} representing the Storage Center's name.
   * @param description A {@code String} representing the Storage Center's description.
   *
   * @return A {@code ResponseEntity} A message if the Storage Center
   *     was successfully created and a HTTP 200 response or,
   *     HTTP 500 reponse if an error occurred.
   */
  @PostMapping("/createCenter")
  public ResponseEntity<?> createStorageCenter(
        @RequestParam("name") String name,
        @RequestParam("description") String description) {
    try {
      StorageCenter storageCenter = new StorageCenter(name, description);
      StorageCenter savedStorageCenter = storageCenterRepository.save(storageCenter);
      String message = "Storage Center with following ID created successfully: ";
      message += savedStorageCenter.getDatabaseId();
      return ResponseEntity.ok(message);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Retrieves a storage center from the database.
   *
   * @param storageCenterId A {@code String} representing the storage center's ID.
   *
   * @return A {@code ResponseEntity} A message if the Storage Center
   *     was successfully retrieved and a HTTP 200 response or,
   *     HTTP 500 reponse if an error occurred or,
   *     HTTP 404 response if the storage center ID was not found.
   */
  @GetMapping("/getCenterInfo")
  public ResponseEntity<?> getStorageCenterInfo(
        @RequestParam("storageCenterId") int storageCenterId) {
    try {
      StorageCenter center = storageCenterRepository.findById(storageCenterId).orElseThrow();
      return ResponseEntity.ok(center.toString());

    } catch (NoSuchElementException e) {
      return handleNotFoundException("Storage Center", storageCenterId);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Deletes a storage center from the database.
   *
   * @param storageCenterId A {@code String} representing the storage center's ID.
   *
   * @return A {@code ResponseEntity} A message if the Storage Center was
   *     successfully deleted and a HTTP 200 response or,
   *     HTTP 500 reponse if an error occurred or,
   *     HTTP 404 response if the storage center ID is not found.
   */
  @DeleteMapping("/deleteCenter")
  public ResponseEntity<?> deleteStorageCenter(
          @RequestParam("storageCenterId") int storageCenterId) {
    try {
      storageCenterRepository.findById(storageCenterId).orElseThrow();
      storageCenterRepository.deleteById(storageCenterId);
      String message = "Storage Center with ID: " + storageCenterId + " was deleted successfully";
      return ResponseEntity.ok(message);

    } catch (NoSuchElementException e) {
      return handleNotFoundException("Storage Center", storageCenterId);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
  * Updates the storage center's name.
  *
  * @param storageCenterId A {@code String} representing the storage center's ID.
  * @param name A {@code String} representing the storage center's name.
  * @return A {@code ResponseEntity} A message if the Storage Center's name was
  *     successfully updated and a HTTP 200 response or,
  *     HTTP 500 reponse if an error occurred or,
  *     HTTP 400 response if the storage center ID or name is incorrectly formatted.
  */
  @PatchMapping("/updateCenterName")
  public ResponseEntity<?> updateStorageCenterName(
        @RequestParam("storageCenterId") int storageCenterId,
        @RequestParam("name") String name) {
    try {
      StorageCenter center = storageCenterRepository.findById(storageCenterId).orElseThrow();
      center.changeName(name);
      storageCenterRepository.save(center);
      return ResponseEntity.ok("Storage Center's name updated");

    } catch (NoSuchElementException e) {
      return handleNotFoundException("Storage Center", storageCenterId);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
  * Updates the storage center's description.
  *
  * @param storageCenterId A {@code String} representing the storage center's ID.
  * @param description A {@code String} representing the storage center's description.
  * @return A {@code ResponseEntity} A message if the Storage Center's description was
  *     successfully updated and a HTTP 200 response or,
  *     HTTP 500 reponse if an error occurred or,
  *     HTTP 400 response if the storage center ID or description is incorrectly formatted.
  */
  @PatchMapping("/updateCenterDescription")
  public ResponseEntity<?> updateStorageCenterDescription(
        @RequestParam("storageCenterId") int storageCenterId,
        @RequestParam("description") String description) {
    try {
      StorageCenter center = storageCenterRepository.findById(storageCenterId).orElseThrow();
      center.changeDescription(description);
      storageCenterRepository.save(center);
      return ResponseEntity.ok("Storage Center's description updated");

    } catch (NoSuchElementException e) {
      return handleNotFoundException("Storage Center", storageCenterId);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
  * Updates the storage center's operating hours. The open and close time must be in the
  * format HH:MM. Day must be an integer between 1 and 7.
  *
  * @param storageCenterId A {@code String} representing the storage center's ID.
  * @param day A {@code String} representing the day of the week.
  * @param open A {@code String} representing the opening time in HH:MM format.
  * @param close A {@code String} representing the closing time in HH:MM format.
  * @return A {@code ResponseEntity} A message if the Storage Center's operating hours was
  *     successfully updated and a HTTP 200 response or,
  *     HTTP 500 reponse if an error occurred or,
  *     HTTP 400 response if the storage center ID, day, open, or close is incorrectly formatted, or
  *     HTTP 404 response if the storage center ID is not found.
  */
  @PatchMapping("/updateCenterHours")
  public ResponseEntity<?> updateCenterHours(
        @RequestParam("storageCenterId") int storageCenterId,
        @RequestParam("day") int day,
        @RequestParam("open") String open,
        @RequestParam("close") String close) {
    try {
      StorageCenter center = storageCenterRepository.findById(storageCenterId).orElseThrow();
      TimeSlot hours = new TimeSlot(open, close);
      center.updateDayHours(hours, day);
      storageCenterRepository.save(center);
      return ResponseEntity.ok("Storage Center's operating hours updated");

    } catch (NoSuchElementException e) {
      return handleNotFoundException("Storage Center", storageCenterId);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
  * Adds an item to the storage center. The type must be FOOD, CLOTHES, or TOILETRIES.
  * The quantity must be a positive integer.
  *
  * @param storageCenterId A {@code String} representing the storage center's ID.
  * @param type A {@code String} representing the item's type.
  * @param name A {@code String} representing the item's name.
  * @param quantity A {@code int} representing the item's quantity.
  * @param expirationDate A {@code String} representing the item's expiration date
  *     in the yyyy-MM-dd format.
  * @return A {@code ResponseEntity} A message if the item was
  *     successfully added to the storage center
  *     and a HTTP 200 response or,  HTTP 500 reponse if an error occurred or,
  *     HTTP 400 response if the storage center ID, type, quantity,
  *     or expiration date is incorrectly formatted.
   */
  @PatchMapping("/checkInItems")
  public ResponseEntity<?> checkInItems(
        @RequestParam("storageCenterId") int storageCenterId,
        @RequestParam("type") String type,
        @RequestParam("name") String name,
        @RequestParam("quantity") int quantity,
        @RequestParam("expirationDate") @DateTimeFormat(pattern = "yyyy-MM-dd")
        String expirationDate) {
    try {
      StorageCenter center = storageCenterRepository.findById(storageCenterId).orElseThrow();
      ItemId itemId = new ItemId(type, name);
      Optional<Item> item = itemRepository.findById(itemId);
      if (item.isPresent()) {
        Item foundItem = item.get();
        foundItem.incrementQuantity(quantity);
        itemRepository.save(foundItem);
        return ResponseEntity.ok("Item quantity updated successfully");
      }

      Item newItem = new Item(itemId, quantity, center, expirationDate);
      itemRepository.save(newItem);
      String message = "Item added to storage center successfully";
      return ResponseEntity.ok(message);

    } catch (NoSuchElementException e) {
      return handleNotFoundException("Storage Center", storageCenterId);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
  * Removes an item from the storage center.
  *
  * @param storageCenterId A {@code String} representing the storage center's ID.
  * @param type A {@code String} representing the item's type.
  * @param name A {@code String} representing the item's name.
  * @param quantity A {@code int} representing the item's quantity.
  * @return A {@code ResponseEntity} A message if the item was
  *     successfully removed from the storage center and a HTTP 200 response or,
  *     HTTP 500 reponse if an error occurred or,
  *     HTTP 400 response if the storage center ID, type, or name is incorrectly formatted.
  */
  @PatchMapping("/checkOutItems")
  public ResponseEntity<?> checkOutItems(
        @RequestParam("storageCenterId") int storageCenterId,
        @RequestParam("type") String type,
        @RequestParam("name") String name,
        @RequestParam("quantity") int quantity) {
    try {
      storageCenterRepository.findById(storageCenterId).orElseThrow();
      ItemId itemId = new ItemId(type, name);
      Optional<Item> searchedItem = itemRepository.findById(itemId);
      if (searchedItem.isEmpty()) {
        String message = "The item was not found in the storage center";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
      }

      Item item = searchedItem.get();
      item.decrementQuantity(quantity);
      if (item.getQuantity() == 0) {
        itemRepository.delete(item);
      } else {
        itemRepository.save(item);
      }
      String message = "Items removed from storage center successfully";
      return ResponseEntity.ok(message);

    } catch (NoSuchElementException e) {
      return handleNotFoundException("Storage Center", storageCenterId);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Get the storage center's inventory.
   *
   * @param storageCenterId A {@code String} representing the storage center's ID.
   * @return A {@code ResponseEntity} A message if the storage center was successfully
   *     found and a HTTP 200 response or,
   *     HTTP 500 reponse if an error occurred or,
   *     404 response if the storage center ID is not found.
   */
  @GetMapping("/listInventory")
  public ResponseEntity<?> listInventory(
        @RequestParam("storageCenterId") int storageCenterId) {
    try {
      StorageCenter storage = storageCenterRepository.findById(storageCenterId).orElseThrow();
      return ResponseEntity.ok(storage.printItems());

    } catch (NoSuchElementException e) {
      return handleNotFoundException("Storage Center", storageCenterId);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  private ResponseEntity<?> handleException(Exception e) {
    boolean isBadRequest = e instanceof IllegalArgumentException
        || e instanceof NumberFormatException
        || e instanceof ParseException
        || e instanceof DateTimeParseException
        || e instanceof DateTimeException;
    if (isBadRequest) {
      return ResponseEntity.badRequest().body(e.toString());
    }

    return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<?> handleNotFoundException(String entityName, int entityId) {
    String message = entityName + " with ID: " + entityId + " was not found";
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
  }
}