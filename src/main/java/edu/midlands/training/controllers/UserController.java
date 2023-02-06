package edu.midlands.training.controllers;


import static edu.midlands.training.constants.StringConstants.CONTEXT_USERS;
import static edu.midlands.training.constants.StringConstants.LOGGER_DELETE_REQUEST_RECEIVED;
import static edu.midlands.training.constants.StringConstants.LOGGER_POST_REQUEST_RECEIVED;
import static edu.midlands.training.constants.StringConstants.LOGGER_PUT_REQUEST_RECEIVED;
import static edu.midlands.training.constants.StringConstants.LOGGER_REQUEST_RECEIVED;

import edu.midlands.training.entities.Product;
import edu.midlands.training.entities.User;
import edu.midlands.training.services.UserService;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller has all the CRUD methods for the user entity
 */
@RestController
@RequestMapping(CONTEXT_USERS)
public class UserController {

  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  /**
   * give you all the users if you pass a null user or user matching an example with non-null user
   *
   * @param user object which can have null or non-null fields, returns status 200
   * @return List of users
   */
  @GetMapping
  public ResponseEntity<List<User>> queryUsers(User user) {
    logger.info(new Date() + LOGGER_REQUEST_RECEIVED + user.toString());

    return new ResponseEntity<>(userService.queryUsers(user), HttpStatus.OK);
  }


  /**
   * Gets user by id.
   *
   * @param id the user's id from the path variable
   * @return the User with said id
   */
  @GetMapping(value = "/{id}")
  public ResponseEntity<User> getUser(@PathVariable Long id) {
    logger.info(new Date() + LOGGER_REQUEST_RECEIVED + id);

    return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
  }

  /**
   * Adds a new user to the database.
   *
   * @param user the user from the request body being added
   * @return the user if everything is correctly added
   */
  @PostMapping
  public ResponseEntity<User> save(@Valid @RequestBody User user) {
    logger.info(new Date() + LOGGER_POST_REQUEST_RECEIVED);

    return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
  }

  /**
   * Update user by id
   *
   * @param id  the id of the user to be updated from the path variable
   * @param user the user's new information from the request body
   * @return the user if input and data is correct
   */
  @PutMapping(value = "/{id}")
  public ResponseEntity<User> updateUserById(
      @PathVariable Long id, @Valid @RequestBody User user) {
    logger.info(new Date() + LOGGER_PUT_REQUEST_RECEIVED + id);

    return new ResponseEntity<>(userService.updateUserById(user,id), HttpStatus.OK);
  }

  /**
   * Delete user by id.
   *
   * @param id the user's id from the path variable
   */
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable Long id) {
    logger.info(new Date() + LOGGER_DELETE_REQUEST_RECEIVED + id);

    userService.deleteUser(id);
  }
}
