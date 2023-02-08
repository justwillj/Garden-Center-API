package edu.midlands.training.repositories;

import edu.midlands.training.entities.User;
import java.util.List;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This will give our User entities basic CRUD
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
