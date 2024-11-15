package com.smartprogrammingbaddies.item;

import org.springframework.data.repository.CrudRepository;

/**
 * The ItemRepository interface is used to store and manage items that can be donated.
 */
public interface ItemRepository extends CrudRepository<Item, ItemId> {
}
