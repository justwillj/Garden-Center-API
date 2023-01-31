package edu.midlands.training.repositories;

import edu.midlands.training.entities.Address;
import edu.midlands.training.entities.Customers;
import java.util.List;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersRepository extends JpaRepository<Customers,Long> {

  // List<Customers> findByAddress(Address address);
}
