package edu.midlands.training.repositories;

import edu.midlands.training.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

  // List<Customers> findByAddress(Address address);
}
