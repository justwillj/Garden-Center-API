package edu.midlands.training.services;

import edu.midlands.training.entities.User;
import edu.midlands.training.exceptions.BadDataResponse;
import edu.midlands.training.exceptions.ConflictData;
import edu.midlands.training.exceptions.ResourceNotFound;
import edu.midlands.training.exceptions.ServiceUnavailable;
import edu.midlands.training.repositories.UserRepository;
import java.util.Arrays;
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


  /**
   * This method will take a user as an optional parameter. If the user is given then it will create
   * a query by example. If nothing is given then we will get all users.
   *
   * @param user - any provided fields will be converted to an exact match AND query
   * @return a list of users that match the query, if not supplied then all the users in the database
   */
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

/**
 * Lookup a User by its id.
 *
 * @param id - the id to lookup
 * @return the user that matches the id
 */
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
    // if we made it down to this pint, we did not find the User
    throw new ResourceNotFound("Could not locate a User with the id: " + id);
  }

  /**
   * Adds a new User to the database.
   *
   * @param user - the user that will be added to the database.
   * @return the new user if the required fields are inputted correctly
   */
  @Override
  public User addUser(User user) {
    String[] employee = new String[]{"EMPLOYEE"};
    String[] admin = new String[]{"ADMIN"};

    if (!Arrays.equals(user.getRoles(), employee)){
      if (!Arrays.equals(user.getRoles(), admin)){
        throw new ConflictData("Please use a valid role!");
      }
    }
      //Checks to see if the email is already taken and if so throws an exceptions
      for (User u : userRepository.findAll()) {
        if (Objects.equals(u.getEmail().toLowerCase(), user.getEmail().toLowerCase())) {
          throw new BadDataResponse("This email is already in use!");
        }
      }
      try {
        return userRepository.save(user);
      } catch (Exception e) {
        throw new ServiceUnavailable(e);
      }

  }

  /**
   * Update an existing User in the database.
   *
   * @param id  - the id of the user to update.
   * @param user - the User information to update.
   * @return the updated user if done correctly
   */
  @Override
  public User updateUserById(User user,Long id) {
    // first, check to make sure the id passed matches the id in the Pet passed
    if (!user.getId().equals(id)) {
      throw new BadDataResponse("User ID must match the ID specified in the URL");
    }
    String[] employee = new String[]{"EMPLOYEE"};
    String[] admin = new String[]{"ADMIN"};

    if (!Arrays.equals(user.getRoles(), employee)){
      if (!Arrays.equals(user.getRoles(), admin)){
        throw new BadDataResponse("Please use a valid role!");
      }
    }

    //If the id of the user we are updating and the endpoint id match, allows the user to keep its
    //current email when updating
    for (User u: userRepository.findAll()){
      if (Objects.equals(user.getId(), u.getId()) && Objects.equals(user.getEmail(), u.getEmail())){
        return userRepository.save(user);
      }
      //Checks to see if the email is already taken and if so throws an exceptions
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
    // if we made it down to this pint, we did not find the User
    throw new ResourceNotFound("Could not locate a User with the id: " + id);
  }










  /**
   * Delete a User from the database.
   *
   * @param id - the id of the user to be deleted.
   */
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
    throw new ResourceNotFound("Could not locate a User with the id: " + id);
  }
}
