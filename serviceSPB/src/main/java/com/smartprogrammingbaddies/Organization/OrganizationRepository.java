package com.smartprogrammingbaddies.organization;

import org.springframework.data.repository.CrudRepository;

/**
 * The OrganizationRepository interface is used to store and manage organizations. It provides
 * methods to add, remove, and list organizations in the database.
 */
public interface OrganizationRepository extends CrudRepository<Organization, Integer> {
}
