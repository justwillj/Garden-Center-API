package edu.midlands.training.services;

import edu.midlands.training.entities.Users;
import edu.midlands.training.exceptions.ServiceUnavailable;
import edu.midlands.training.repositories.UsersRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

  private final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

  @Autowired
  private UsersRepository usersRepository;

  @Override
  public List<Users> queryUsers(Users users) {
    try {
      if (Users.isEmpty()) {
        return usersRepository.findAll();
      } else {
        Example<Users> usersExample = Example.of(users);
        return usersRepository.findAll(usersExample);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  @Override
  public Users getUser(Long id) {
    return null;
  }
}
