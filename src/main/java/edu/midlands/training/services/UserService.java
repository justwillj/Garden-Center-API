package edu.midlands.training.services;

import edu.midlands.training.entities.User;
import java.util.List;

public interface UserService {

  List<User>queryUsers(User user);
  User getUser(Long id);
  User addUser(User user);

  User updateUserById(User user, Long id);

  void deleteUser(Long id);

}
