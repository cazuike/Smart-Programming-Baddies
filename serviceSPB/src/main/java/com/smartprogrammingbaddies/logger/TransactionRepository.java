package com.smartprogrammingbaddies.logger;

import org.springframework.data.repository.CrudRepository;

/**
 * This class contains the TransactionRepository interface.
 */
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
}
