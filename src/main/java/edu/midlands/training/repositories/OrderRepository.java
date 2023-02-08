package edu.midlands.training.repositories;

import edu.midlands.training.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This will give our Order entities basic CRUD
 */
@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

}
