package edu.midlands.training.services;

import edu.midlands.training.entities.Users;
import java.util.List;
import org.h2.engine.User;

public interface UsersService {

  List<Users>queryUsers(Users users);
  Users getUser(Long id);

  Users addUser(Users user);

  Users updateUserById(Users user, Long id);

  void deleteUser(Long id);

}
