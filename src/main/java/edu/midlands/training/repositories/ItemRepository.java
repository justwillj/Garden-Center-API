package edu.midlands.training.repositories;

import edu.midlands.training.entities.Item;
import edu.midlands.training.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
