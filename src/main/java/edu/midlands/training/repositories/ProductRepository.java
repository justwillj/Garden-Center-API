package edu.midlands.training.repositories;

import edu.midlands.training.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This will give our Product entities basic CRUD
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
