package edu.midlands.training.services;

import edu.midlands.training.entities.User;
import edu.midlands.training.exceptions.BadDataResponse;
import edu.midlands.training.exceptions.ConflictData;
import edu.midlands.training.exceptions.ResourceNotFound;
import edu.midlands.training.exceptions.ServiceUnavailable;
import edu.midlands.training.repositories.UserRepository;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private UserRepository userRepository;

  @Override
  public List<User> queryUsers(User user) {
    try {
      if (user.isEmpty()) {
        return userRepository.findAll();
      } else {
        Example<User> usersExample = Example.of(user);
        return userRepository.findAll(usersExample);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  @Override
  public User getUser(Long id) {
    try {
      User user = userRepository.findById(id).orElse(null);

      if (user != null) {
        return user;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
    // if we made it down to this pint, we did not find the Pet
    throw new ResourceNotFound("Could not locate a User with the id: " + id);
  }

  @Override
  public User addUser(User user) {

    for (User u: userRepository.findAll()){
      if (Objects.equals(u.getEmail().toLowerCase(), user.getEmail().toLowerCase())){
        throw new ConflictData("This email is already in use!");
      }
    }
    try {
      return userRepository.save(user);
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  @Override
  public User updateUserById(User user,Long id) {
    // first, check to make sure the id passed matches the id in the Pet passed
    if (!user.getId().equals(id)) {
      throw new BadDataResponse("User ID must match the ID specified in the URL");
    }

    for (User u: userRepository.findAll()){
      if (Objects.equals(user.getId(), u.getId()) && Objects.equals(user.getEmail(), u.getEmail())){
        return userRepository.save(user);
      }
      if (Objects.equals(u.getEmail().toLowerCase(), user.getEmail().toLowerCase())){
        throw new ConflictData("This email is already in use!");
      }
    }

    try {
      User userFromDb = userRepository.findById(id).orElse(null);
      if (userFromDb != null) {
        return userRepository.save(user);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this pint, we did not find the Pet
    throw new ResourceNotFound("Could not locate a Pet with the id: " + id);
  }

  @Override
  public void deleteUser(Long id) {
    try {
      if (userRepository.existsById(id)) {
        userRepository.deleteById(id);
        return;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this pint, we did not find the Pet
    throw new ResourceNotFound("Could not locate a Pet with the id: " + id);
  }
}
