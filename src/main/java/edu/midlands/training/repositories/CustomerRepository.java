package edu.midlands.training.repositories;

import edu.midlands.training.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This will give our Customer entities basic CRUD
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
