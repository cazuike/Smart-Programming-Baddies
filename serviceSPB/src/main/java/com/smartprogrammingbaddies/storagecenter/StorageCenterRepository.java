package com.smartprogrammingbaddies.storagecenter;

import org.springframework.data.repository.CrudRepository;

/**
* The StorageCenterRepository interface is used to store and manage goods such
* as foods, toiletries, or clothes. It provides methods to add, remove, and list items in the
* storage.
*/
public interface StorageCenterRepository extends CrudRepository<StorageCenter, Integer> {
}
