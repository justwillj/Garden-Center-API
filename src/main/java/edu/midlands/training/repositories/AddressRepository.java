package edu.midlands.training.repositories;

import edu.midlands.training.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This will give our Address entities basic CRUD
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
