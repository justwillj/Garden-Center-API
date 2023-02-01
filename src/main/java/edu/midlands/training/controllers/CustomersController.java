package edu.midlands.training.controllers;

import static edu.midlands.training.constants.StringConstants.CONTEXT_CUSTOMERS;
import static edu.midlands.training.constants.StringConstants.CONTEXT_USERS;
import static edu.midlands.training.constants.StringConstants.LOGGER_REQUEST_RECEIVED;

import edu.midlands.training.entities.Address;
import edu.midlands.training.entities.Customers;
import edu.midlands.training.entities.Users;
import edu.midlands.training.services.CustomersService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CONTEXT_CUSTOMERS)
public class CustomersController {

  private final Logger logger = LoggerFactory.getLogger(CustomersController.class);

  @Autowired
  private CustomersService customersService;


  @GetMapping
  public ResponseEntity<List<Customers>> queryCustomers(Customers customers) {
    logger.info(new Date() + LOGGER_REQUEST_RECEIVED + customers.toString());

    return new ResponseEntity<>(customersService.queryCustomers(customers), HttpStatus.OK);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Customers> getCustomer(@PathVariable Long id) {
    logger.info(new Date() + LOGGER_REQUEST_RECEIVED + id);

    return new ResponseEntity<>(customersService.getCustomer(id), HttpStatus.OK);
  }


}
