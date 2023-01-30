package edu.midlands.training.controllers;


import static edu.midlands.training.constants.StringConstants.CONTEXT_USERS;
import static edu.midlands.training.constants.StringConstants.LOGGER_REQUEST_RECEIVED;

import edu.midlands.training.entities.Users;
import edu.midlands.training.services.UsersService;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CONTEXT_USERS)
public class UsersController {

  private final Logger logger = LoggerFactory.getLogger(UsersController.class);

  @Autowired
  private UsersService usersService;


  @GetMapping
  public ResponseEntity<List<Users>> queryUsers(Users users) {
    logger.info(new Date() + LOGGER_REQUEST_RECEIVED + users.toString());

    return new ResponseEntity<>(usersService.queryUsers(users), HttpStatus.OK);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Users> getUser(@PathVariable Long id) {
    logger.info(new Date() + LOGGER_REQUEST_RECEIVED + id);

    return new ResponseEntity<>(usersService.getUser(id), HttpStatus.OK);
  }

}
