package com.smartprogrammingbaddies.storageCenter;

import com.smartprogrammingbaddies.item.Item;
import com.smartprogrammingbaddies.item.ItemId;
import com.smartprogrammingbaddies.item.ItemRepository;
import com.smartprogrammingbaddies.utils.DateParser;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
      return new ResponseEntity<>(message, HttpStatus.OK);
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
   *     HTTP 500 reponse if an error occurred.
   */
  @GetMapping("/getCenter")
  public ResponseEntity<?> getStorageCenter(
        @RequestParam("storageCenterId") String storageCenterId) {
    try {
      int id = Integer.parseInt(storageCenterId);
      StorageCenter storageCenter = storageCenterRepository.findById(id).get();
      return new ResponseEntity<>(storageCenter, HttpStatus.OK);
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
   *     HTTP 500 reponse if an error occurred.
   */
  @DeleteMapping("/deleteCenter")
  public ResponseEntity<?> deleteStorageCenter(
          @RequestParam("storageCenterId") String storageCenterId) {
    try {
      int id = Integer.parseInt(storageCenterId);
      storageCenterRepository.deleteById(id);
      boolean deleted = !storageCenterRepository.existsById(id);
      if (!deleted) {
        String message = "Storage Center with ID: " + storageCenterId + " was not deleted";
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
      }
      String message = "Storage Center with ID: " + storageCenterId + " was deleted successfully";
      return new ResponseEntity<>(message, HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  private ResponseEntity<?> handleException(Exception e) {
    System.out.println(e.toString());
    return new ResponseEntity<>("An Error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
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
  @PostMapping("/updateCenterName")
  public ResponseEntity<?> updateStorageCenterName(
        @RequestParam("storageCenterId") String storageCenterId,
        @RequestParam("name") String name) {
    try {
      int id = Integer.parseInt(storageCenterId);
      StorageCenter storage = storageCenterRepository.findById(id).get();
      boolean changed = storage.changeName(name);
      if (!changed) {
        String message = "The new name is not valid";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
      }
      storageCenterRepository.save(storage);
      return new ResponseEntity<>("Storage Center's name updated successfully", HttpStatus.OK);
    } catch (NumberFormatException e) {
      String message = "The storage center ID is incorrectly formatted";
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
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
  @PostMapping("/updateCenterDescription")
  public ResponseEntity<?> updateStorageCenterDescription(
        @RequestParam("storageCenterId") String storageCenterId,
        @RequestParam("description") String description) {
    try {
      int id = Integer.parseInt(storageCenterId);
      StorageCenter storage = storageCenterRepository.findById(id).orElseThrow();
      boolean changed = storage.changeDescription(description);
      if (!changed) {
        String message = "The new description is not valid";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
      }
      storageCenterRepository.save(storage);
      return new ResponseEntity<>("Storage Center's description updated successfully", HttpStatus.OK);
    } catch (NumberFormatException e) {
      String message = "The storage center ID is incorrectly formatted";
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    } catch (NoSuchElementException e) {
      String message = "The storage center ID was not found in the database";
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    catch (Exception e) {
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
  *     HTTP 400 response if the storage center ID, day, open, or close is incorrectly formatted.
  */
  @PostMapping("/updateCenterOperatingHours")
  public ResponseEntity<?> updateStorageCenterOperatingHours(
        @RequestParam("storageCenterId") String storageCenterId,
        @RequestParam("day") int day,
        @RequestParam("open") String open,
        @RequestParam("close") String close) {
    try {
      int id = Integer.parseInt(storageCenterId);
      StorageCenter storage = storageCenterRepository.findById(id).orElseThrow();
      LocalTime openTime = LocalTime.parse(open);
      LocalTime closeTime = LocalTime.parse(close);
      if (day < 1 || day > 7) {
        String message = "The day of the week is not valid. Must be between 1 and 7";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
      }
      boolean changed = storage.updateDayHours(openTime, closeTime, DayOfWeek.of(day));
      if (!changed) {
        String message = "The new operating hours are not valid";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
      }
      storageCenterRepository.save(storage);
      return new ResponseEntity<>("Storage Center's operating hours updated successfully", HttpStatus.OK);
    } catch (NumberFormatException e) {
      String message = "The storage center ID is incorrectly formatted";
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    } catch (DateTimeParseException  e) {
      String message = "The open or close time is incorrectly formatted";
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
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
  * @return A {@code ResponseEntity} A message if the item was
  *     successfully added to the storage center
  *     and a HTTP 200 response or,  HTTP 500 reponse if an error occurred or,
  *     HTTP 400 response if the storage center ID, type, quantity,
  *     or expiration date is incorrectly formatted.
   */
  @PostMapping("/checkInItem")
  public ResponseEntity<?> checkInItem(
        @RequestParam("storageCenterId") String storageCenterId,
        @RequestParam("type") String type,
        @RequestParam("name") String name,
        @RequestParam("quantity") int quantity,
        @RequestParam("storage") String expirationDate) {
    try {
      int id = Integer.parseInt(storageCenterId);
      StorageCenter storage = storageCenterRepository.findById(id).orElseThrow();
      ItemId itemId = new ItemId(ItemId.ItemType.valueOf(type), name);
      Date date = DateParser.StringToNumericDate(expirationDate);
      Item item = new Item(itemId,  quantity, storage, date);
      storage.checkInItem(type, item);
      storageCenterRepository.save(storage);
      itemRepository.save(item);
      String message = "Item added to storage center successfully";
      return new ResponseEntity<>(message, HttpStatus.OK);
    } catch (NumberFormatException e) {
      String message = "The storage center ID is incorrectly formatted";
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    } catch (NoSuchElementException e) {
      String message = "The storage center ID was not found in the database";
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    } catch (ParseException e) {
      String message = "The expiration date is incorrectly formatted";
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
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
  * @return A {@code ResponseEntity} A message if the item was
  *     successfully removed from the storage center and a HTTP 200 response or,
  *     HTTP 500 reponse if an error occurred or,
  *     HTTP 400 response if the storage center ID, type, or name is incorrectly formatted.
  */
  @DeleteMapping("/checkOutItem")
  public ResponseEntity<?> checkOutItem(
        @RequestParam("storageCenterId") String storageCenterId,
        @RequestParam("type") String type,
        @RequestParam("name") String name) {
    try {
      int id = Integer.parseInt(storageCenterId);
      StorageCenter storage = storageCenterRepository.findById(id).orElseThrow();
      ItemId itemId = new ItemId(ItemId.ItemType.valueOf(type), name);
      Item item = itemRepository.findById(itemId).orElseThrow();
      storage.checkOutItem(name, item);
      storageCenterRepository.save(storage);
      itemRepository.delete(item);
      String message = "Item removed from storage center successfully";
      return new ResponseEntity<>(message, HttpStatus.OK);
    } catch (NumberFormatException e) {
      String message = "The storage center ID is incorrectly formatted";
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    } catch (NoSuchElementException e) {
      String message = "The storage center ID was not found in the database";
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
  * Lists all items in the storage center.
  *
  * @param storageCenterId A {@code String} representing the storage center's ID.
  * @return A {@code ResponseEntity} A message if the items were
  *     successfully retrieved from the storage center and a HTTP 200 response or,
  *     HTTP 500 reponse if an error occurred or,
  *     HTTP 400 response if the storage center ID is incorrectly formatted.
  */
  @GetMapping("/getItems")
  public ResponseEntity<?> getItems(
        @RequestParam("storageCenterId") String storageCenterId) {
    try {
      int id = Integer.parseInt(storageCenterId);
      StorageCenter storage = storageCenterRepository.findById(id).orElseThrow();
      Set<Item> items = storage.getItems();
      if (items.isEmpty()) {
        String message = "No items found in storage center";
        return new ResponseEntity<>(message, HttpStatus.OK);
      }
      String message = storage.printItems();
      return new ResponseEntity<>(storage.getItems(), HttpStatus.OK);
    } catch (NumberFormatException e) {
      String message = "The storage center ID is incorrectly formatted";
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    } catch (NoSuchElementException e) {
      String message = "The storage center ID was not found in the database";
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
  * Updates the quantity of an item in the storage center.
  *
  * @param storageCenterId A {@code String} representing the storage center's ID.
  * @param type A {@code String} representing the item's type.
  * @param name A {@code String} representing the item's name.
  * @param quantity A {@code int} representing the item's quantity.
  * @return A {@code ResponseEntity} A message if the item's quantity was
  *     successfully updated and a HTTP 200 response or,
  *     HTTP 500 reponse if an error occurred or,
  *     HTTP 400 response if the storage center ID, type, name, or quantity is incorrectly formatted.
  */
  @PostMapping("/updateItemQuantity")
  public ResponseEntity<?> updateItemQuantity(
        @RequestParam("storageCenterId") String storageCenterId,
        @RequestParam("type") String type,
        @RequestParam("name") String name,
        @RequestParam("quantity") int quantity) {
    try {
      int id = Integer.parseInt(storageCenterId);
      StorageCenter storage = storageCenterRepository.findById(id).orElseThrow();
      ItemId itemId = new ItemId(ItemId.ItemType.valueOf(type), name);
      Item item = itemRepository.findById(itemId).orElseThrow();
      boolean changed = item.setQuantity(quantity);
      if (!changed) {
        String message = "The new quantity is not valid";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
      }
      itemRepository.save(item);
      return new ResponseEntity<>("Item's quantity updated successfully", HttpStatus.OK);
    } catch (NumberFormatException e) {
      String message = "The storage center ID is incorrectly formatted";
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    } catch (NoSuchElementException e) {
      String message = "The storage center ID was not found in the database";
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return handleException(e);
    }
  }
}