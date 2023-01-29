package edu.midlands.training.services;

import edu.midlands.training.entities.Users;
import java.util.List;

public interface UsersService {

  List<Users>queryUsers(Users users);
  Users getUser(Long id);

}
