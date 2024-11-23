package com.smartprogrammingbaddies.storagecenter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.smartprogrammingbaddies.item.Item;
import com.smartprogrammingbaddies.item.ItemId;
import com.smartprogrammingbaddies.item.ItemRepository;
import com.smartprogrammingbaddies.logger.Transaction;
import com.smartprogrammingbaddies.logger.TransactionRepository;
import java.text.ParseException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

/**
 * API endpoint tests for the StorageCenterController class.
 */
@ActiveProfiles("test")
@WebMvcTest(StorageCenterController.class)
public class StorageCenterControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StorageCenterRepository storageCenterRepository;

  @MockBean
  private ItemRepository itemRepository;

  @MockBean
  private TransactionRepository transactionRepository;

  @MockBean
  private Item item;

  @MockBean
  private ItemId itemId;

  @MockBean
  private Transaction transaction;

  /**
   * Sets up the tests objects and mocked repository actions.
   *
   * @throws ParseException if the setup fails
   */
  @BeforeEach
  public void setUp() throws ParseException {
    StorageCenter testCenter = new StorageCenter("CUFP", "Food Pantry");
    when(storageCenterRepository.save(any(StorageCenter.class))).thenReturn(testCenter);
    when(storageCenterRepository.findById(1)).thenReturn(Optional.of(testCenter));

    ItemId itemId = new ItemId("FOOD", "Canned Beans");
    Item testItem = new Item(itemId, 10, testCenter, null);
    when(itemRepository.save(any(Item.class))).thenReturn(testItem);
    when(itemRepository.findById(itemId)).thenReturn(Optional.of(testItem));

    ItemId itemId2 = new ItemId("FOOD", "Canned Corn");
    when(itemRepository.findById(itemId2)).thenReturn(Optional.empty());
  }

  /**
   * Tests the createCenter method.
   */
  @Test
  public void testCreateCenter() throws Exception {
    ResultActions result = mockMvc.perform(post("/createCenter")
              .param("name", "CUFP")
              .param("description", "Food Pantry"));

    result.andExpect(status().isOk());
  }

  /**
   * Tests the createCenter method with an invalid name.
   */
  @Test
  public void testcreateCenterInvalidName() throws Exception {
    ResultActions result = mockMvc.perform(post("/createCenter")
              .param("name", "")
              .param("description", "Food Pantry"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(post("/createCenter")
              .param("name", " ")
              .param("description", "Food Pantry"));
    result.andExpect(status().isBadRequest());
  }

  /**
   * Tests the createCenter method with an invalid description.
   */
  @Test
  public void testcreateCenterInvalidDescription() throws Exception {
    ResultActions result = mockMvc.perform(post("/createCenter")
              .param("name", "CUFP")
              .param("description", ""));

    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(post("/createCenter")
              .param("name", "CUFP")
              .param("description", " "));
    result.andExpect(status().isBadRequest());
  }

  /**
   * Test the getCenter method with an invalid id.
   */
  @Test
  public void testGetCenterInvalidId() throws Exception {
    ResultActions result = mockMvc.perform(get("/getCenter")
              .param("storageCenterId", "2"));
    result.andExpect(status().isNotFound());
  }

  /**
   * Test the deleteCenter method.
   */
  @Test
  public void testDeleteCenter() throws Exception {
    ResultActions result = mockMvc.perform(delete("/deleteCenter")
              .param("storageCenterId", "1"));
    result.andExpect(status().isOk());
  }

  /**
   * Test the deleteCenter method with an invalid id.
   */
  @Test
  public void testDeleteCenterInvalidId() throws Exception {
    ResultActions result = mockMvc.perform(delete("/deleteCenter")
              .param("storageCenterId", "2"));
    result.andExpect(status().isNotFound());
  }

  /**
   * Test the updateCenter's name method.
   */
  @Test
  public void testUpdateCenterName() throws Exception {
    ResultActions result = mockMvc.perform(patch("/updateCenterName")
              .param("storageCenterId", "1")
              .param("name", "CUGSFP"));
    result.andExpect(status().isOk());
  }

  /**
   * Test the updateCenter's name method with an invalid id.
   */
  @Test
  public void testUpdateCenterNameInvalidId() throws Exception {
    ResultActions result = mockMvc.perform(patch("/updateCenterName")
              .param("storageCenterId", "2")
              .param("name", "CUGSFP"));
    result.andExpect(status().isNotFound());
  }

  /**
   * Test the updateCenter's name method with an invalid name.
   */
  @Test
  public void testUpdateCenterNameInvalidName() throws Exception {
    ResultActions result = mockMvc.perform(patch("/updateCenterName")
              .param("storageCenterId", "1")
              .param("name", ""));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(patch("/updateCenterName")
              .param("storageCenterId", "1")
              .param("name", " "));
    result.andExpect(status().isBadRequest());
  }

  /**
   * Test the updateCenter's description method.
   */
  @Test
  public void testUpdateCenterDescription() throws Exception {
    ResultActions result = mockMvc.perform(patch("/updateCenterDescription")
              .param("storageCenterId", "1")
              .param("description", "Clothes Donation Center"));
    result.andExpect(status().isOk());
  }

  /**
   * Test the updateCenter's description method with an invalid id.
   */
  @Test
  public void testUpdateCenterDescriptionInvalidId() throws Exception {
    ResultActions result = mockMvc.perform(patch("/updateCenterDescription")
              .param("storageCenterId", "2")
              .param("description", "Clothes Donation Center"));
    result.andExpect(status().isNotFound());
  }

  /**
   * Test the updateCenter's description method with an invalid description.
   */
  // @Test
  public void testUpdateCenterDescriptionInvalidDescription() throws Exception {
    ResultActions result = mockMvc.perform(patch("/updateCenterDescription")
              .param("storageCenterId", "1")
              .param("description", ""));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(patch("/updateCenterDescription")
              .param("storageCenterId", "1")
              .param("description", " "));
    result.andExpect(status().isBadRequest());
  }

  /**
   * Test the updateCenter's updateCenterHours.
   */
  @Test
  public void testUpdateCenterHours() throws Exception {
    ResultActions result = mockMvc.perform(patch("/updateCenterHours")
              .param("storageCenterId", "1")
              .param("day", "1")
              .param("open", "08:00")
              .param("close", "17:00"));
    result.andExpect(status().isOk());
  }

  /**
   * Test the updateCenter's updateCenterHours with an invalid id.
   */
  @Test
  public void testUpdateCenterHoursInvalidId() throws Exception {
    ResultActions result = mockMvc.perform(patch("/updateCenterHours")
              .param("storageCenterId", "2")
              .param("day", "1")
              .param("open", "08:00")
              .param("close", "17:00"));
    result.andExpect(status().isNotFound());
  }

  /**
   * Test the updateCenter's updateCenterHours with an invalid day.
   */
//  @Test
//  public void testUpdateCenterHoursInvalidDay() throws Exception {
//    ResultActions result = mockMvc.perform(patch("/updateCenterHours")
//              .param("storageCenterId", "1")
//              .param("day", "8")
//              .param("open", "08:00")
//              .param("close", "17:00"));
//    result.andExpect(status().isBadRequest());
//  }

  /**
   * Test the updateCenter's updateCenterHours with an invalid hour.
   */
  @Test
  public void testUpdateCenterHoursInvalidHour() throws Exception {
    ResultActions result = mockMvc.perform(patch("/updateCenterHours")
              .param("storageCenterId", "1")
              .param("day", "1")
              .param("open", "08:00:00a")
              .param("close", "17:00"));
    result.andExpect(status().isBadRequest());
  }

  /**
   * Test the checkInItems function.
   */
  @Test
  public void testCheckInItems() throws Exception {
    ResultActions result = mockMvc.perform(patch("/checkInItems")
              .param("storageCenterId", "1")
              .param("type", "FOOD")
              .param("name", "Canned Beans")
              .param("quantity", "10")
              .param("expirationDate", "2024-01-01"));
    result.andExpect(status().isOk());

    result = mockMvc.perform(patch("/checkInItems")
              .param("storageCenterId", "1")
              .param("type", "FOOD")
              .param("name", "Canned Corn")
              .param("quantity", "10")
              .param("expirationDate", "2024-01-01"));
    result.andExpect(status().isOk());
  }

  /**
   * Test the checkInItems function with an invalid id.
   */
  @Test
  public void testCheckInItemsInvalidId() throws Exception {
    ResultActions result = mockMvc.perform(patch("/checkInItems")
              .param("storageCenterId", "2")
              .param("type", "FOOD")
              .param("name", "Canned Beans")
              .param("quantity", "10")
              .param("expirationDate", "2024-01-01"));
    result.andExpect(status().isNotFound());
  }

  /**
   * Test the checkInItems function with an invalid type.
  //  */
  @Test
  public void testCheckInItemsInvalidType() throws Exception {
    ResultActions result = mockMvc.perform(patch("/checkInItems")
              .param("storageCenterId", "1")
              .param("type", "")
              .param("name", "Canned Beans")
              .param("quantity", "10")
              .param("expirationDate", "2024-01-01"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(patch("/checkInItems")
              .param("storageCenterId", "1")
              .param("type", " ")
              .param("name", "Canned Beans")
              .param("quantity", "10")
              .param("expirationDate", "2024-01-01"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(patch("/checkInItems")
              .param("storageCenterId", "1")
              .param("type", "INVALID")
              .param("name", "Canned Beans")
              .param("quantity", "10")
              .param("expirationDate", "2024-01-01"));
  }

  /**
   * Test the checkInItems function with an invalid name.
   */
  @Test
  public void testCheckInItemsInvalidName() throws Exception {
    ResultActions result = mockMvc.perform(patch("/checkInItems")
              .param("storageCenterId", "1")
              .param("type", "FOOD")
              .param("name", "")
              .param("quantity", "10")
              .param("expirationDate", "2024-01-01"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(patch("/checkInItems")
              .param("storageCenterId", "1")
              .param("type", "FOOD")
              .param("name", " ")
              .param("quantity", "10")
              .param("expirationDate", "2024-01-01"));
    result.andExpect(status().isBadRequest());
  }

  /**
   * Test the checkInItems function with an invalid quantity.
   */
  @Test
  public void testCheckInItemsInvalidQuantity() throws Exception {
    ResultActions result = mockMvc.perform(patch("/checkInItems")
              .param("storageCenterId", "1")
              .param("type", "FOOD")
              .param("name", "Canned Beans")
              .param("quantity", "-1")
              .param("expirationDate", "2024-01-01"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(patch("/checkInItems")
              .param("storageCenterId", "1")
              .param("type", "FOOD")
              .param("name", "Canned Beans")
              .param("quantity", "0")
              .param("expirationDate", "2024-01-01"));
    result.andExpect(status().isBadRequest());
  }

  /**
   * Test the checkInItems function with an invalid expiration date.
   */
//  @Test
//  public void testCheckInItemsInvalidExpirationDate() throws Exception {
//    ResultActions result = mockMvc.perform(patch("/checkInItems")
//              .param("storageCenterId", "1")
//              .param("type", "FOOD")
//              .param("name", "Canned Corn")
//              .param("quantity", "10")
//              .param("expirationDate", "abc"));
//    result.andExpect(status().isBadRequest());
//  }

  /**
   * Test the checkOutItems function when the item is not found.
   */
  @Test
  public void testCheckOutItemsItemNotFound() throws Exception {
    ResultActions result = mockMvc.perform(patch("/checkOutItems")
              .param("storageCenterId", "1")
              .param("type", "FOOD")
              .param("name", "Canned Corn")
              .param("quantity", "10"));
    result.andExpect(status().isNotFound());
  }

  /**
   * Test the checkOutItems function.
   */
  @Test
  public void testCheckOutItems() throws Exception {
    ResultActions result = mockMvc.perform(patch("/checkOutItems")
              .param("storageCenterId", "1")
              .param("type", "FOOD")
              .param("name", "Canned Beans")
              .param("quantity", "10"));
    result.andExpect(status().isOk());
  }

  /**
   * Test the checkOutItems where the item is should not deleted.
   */
  @Test
  public void testCheckOutItemsItemNotDeleted() throws Exception {
    ResultActions result = mockMvc.perform(patch("/checkOutItems")
              .param("storageCenterId", "1")
              .param("type", "FOOD")
              .param("name", "Canned Beans")
              .param("quantity", "5"));
    result.andExpect(status().isOk());
  }

  /**
   * Test the checkOutItems function with an invalid id.
   */
  @Test
  public void testCheckOutItemsInvalidId() throws Exception {
    ResultActions result = mockMvc.perform(patch("/checkOutItems")
              .param("storageCenterId", "2")
              .param("type", "FOOD")
              .param("name", "Canned Beans")
              .param("quantity", "10"));
    result.andExpect(status().isNotFound());
  }

  /**
   * Test the checkOutItems function with an invalid type.
   */
  @Test
  public void testCheckOutItemsInvalidType() throws Exception {
    ResultActions result = mockMvc.perform(patch("/checkOutItems")
              .param("storageCenterId", "1")
              .param("type", "")
              .param("name", "Canned Beans")
              .param("quantity", "10"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(patch("/checkOutItems")
              .param("storageCenterId", "1")
              .param("type", " ")
              .param("name", "Canned Beans")
              .param("quantity", "10"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(patch("/checkOutItems")
              .param("storageCenterId", "1")
              .param("type", "INVALID")
              .param("name", "Canned Beans")
              .param("quantity", "10"));
    result.andExpect(status().isBadRequest());
  }

  /**
   * Test the checkOutItems function with an invalid name.
   */
  @Test
  public void testCheckOutItemsInvalidName() throws Exception {
    ResultActions result = mockMvc.perform(patch("/checkOutItems")
              .param("storageCenterId", "1")
              .param("type", "FOOD")
              .param("name", "")
              .param("quantity", "10"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(patch("/checkOutItems")
              .param("storageCenterId", "1")
              .param("type", "FOOD")
              .param("name", " ")
              .param("quantity", "10"));
    result.andExpect(status().isBadRequest());
  }

  /**
   * Test the checkOutItems function with an invalid quantity.
   */
  @Test
  public void testCheckOutItemsInvalidQuantity() throws Exception {
    ResultActions result = mockMvc.perform(patch("/checkOutItems")
              .param("storageCenterId", "1")
              .param("type", "FOOD")
              .param("name", "Canned Beans")
              .param("quantity", "-1"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(patch("/checkOutItems")
              .param("storageCenterId", "1")
              .param("type", "FOOD")
              .param("name", "Canned Beans")
              .param("quantity", "0"));
    result.andExpect(status().isBadRequest());

    result = mockMvc.perform(patch("/checkOutItems")
              .param("storageCenterId", "1")
              .param("type", "FOOD")
              .param("name", "Canned Beans")
              .param("quantity", "11"));
    result.andExpect(status().isBadRequest());
  }

  /**
   * Test the listInventory function.
   */
  @Test
  public void testListInventory() throws Exception {
    ResultActions result = mockMvc.perform(get("/listInventory")
              .param("storageCenterId", "1"));
    result.andExpect(status().isOk());
  }

  /**
   * Test the listInventory function with an invalid id.
   */
  @Test
  public void testListInventoryInvalidId() throws Exception {
    ResultActions result = mockMvc.perform(get("/listInventory")
              .param("storageCenterId", "2"));
    result.andExpect(status().isNotFound());
  }

  /**
   * Test the getCenterInfo function.
   */
  @Test
  public void testGetCenterInfo() throws Exception {
    ResultActions result = mockMvc.perform(get("/getCenterInfo")
            .param("storageCenterId", "1"));
    result.andExpect(status().isOk());
  }

  /**
   * Test the getCenterInfo function with an invalid id.
   */
  @Test
  public void testGetCenterInfoInvalidId() throws Exception {
    ResultActions result = mockMvc.perform(get("/getCenterInfo")
            .param("storageCenterId", "2"));
    result.andExpect(status().isNotFound());
  }

  /**
   * Test the createCenter function with internal server error.
   */
  @Test
  public void testCreateEventServerFail() throws Exception {
    when(storageCenterRepository.save(any(StorageCenter.class))).thenThrow(new RuntimeException());
    ResultActions result = mockMvc.perform(post("/createCenter")
              .param("name", "CUFP")
              .param("description", "Food Pantry"));
    result.andExpect(status().isInternalServerError());
  }

  /**
   * Test the deleteCenter function with internal server error.
   */
  @Test
  public void testDeleteCenterServerFail() throws Exception {
    when(storageCenterRepository.findById(1)).thenThrow(new RuntimeException());
    ResultActions result = mockMvc.perform(delete("/deleteCenter")
              .param("storageCenterId", "1"));
    result.andExpect(status().isInternalServerError());
  }

  /**
   * Test the updateCenterName function with internal server error.
   */
  @Test
  public void testUpdateCenterNameServerFail() throws Exception {
    when(storageCenterRepository.findById(1)).thenThrow(new RuntimeException());
    ResultActions result = mockMvc.perform(patch("/updateCenterName")
              .param("storageCenterId", "1")
              .param("name", "CUGSFP"));
    result.andExpect(status().isInternalServerError());
  }

  /**
   * Test the updateCenterDescription function with internal server error.
   */
  @Test
  public void testUpdateCenterDescriptionServerFail() throws Exception {
    when(storageCenterRepository.findById(1)).thenThrow(new RuntimeException());
    ResultActions result = mockMvc.perform(patch("/updateCenterDescription")
              .param("storageCenterId", "1")
              .param("description", "Clothes Donation Center"));
    result.andExpect(status().isInternalServerError());
  }

  /**
   * Test the updateCenterHours function with internal server error.
   */
  @Test
  public void testUpdateCenterHoursServerFail() throws Exception {
    when(storageCenterRepository.findById(1)).thenThrow(new RuntimeException());
    ResultActions result = mockMvc.perform(patch("/updateCenterHours")
              .param("storageCenterId", "1")
              .param("day", "1")
              .param("open", "08:00")
              .param("close", "17:00"));
    result.andExpect(status().isInternalServerError());
  }

  /**
   * Test the checkInItems function with internal server error.
   */
  @Test
  public void testCheckInItemsServerFail() throws Exception {
    when(storageCenterRepository.findById(1)).thenThrow(new RuntimeException());
    ResultActions result = mockMvc.perform(patch("/checkInItems")
              .param("storageCenterId", "1")
              .param("type", "FOOD")
              .param("name", "Canned Beans")
              .param("quantity", "10")
              .param("expirationDate", "2024-01-01"));
    result.andExpect(status().isInternalServerError());
  }

  /**
   * Test the checkOutItems function with internal server error.
   */
  @Test
  public void testCheckOutItemsServerFail() throws Exception {
    when(storageCenterRepository.findById(1)).thenThrow(new RuntimeException());
    ResultActions result = mockMvc.perform(patch("/checkOutItems")
              .param("storageCenterId", "1")
              .param("type", "FOOD")
              .param("name", "Canned Beans")
              .param("quantity", "10"));
    result.andExpect(status().isInternalServerError());
  }

  /**
   * Test the listInventory function with internal server error.
   */
  @Test
  public void testListInventoryServerFail() throws Exception {
    when(storageCenterRepository.findById(1)).thenThrow(new RuntimeException());
    ResultActions result = mockMvc.perform(get("/listInventory")
              .param("storageCenterId", "1"));
    result.andExpect(status().isInternalServerError());
  }

  /**
   * Test the getCenterInfo function with internal server error.
   */
  @Test
  public void testGetCenterInfoServerFail() throws Exception {
    when(storageCenterRepository.findById(1)).thenThrow(new RuntimeException());
    ResultActions result = mockMvc.perform(get("/getCenterInfo")
              .param("storageCenterId", "1"));
    result.andExpect(status().isInternalServerError());
  }
}
