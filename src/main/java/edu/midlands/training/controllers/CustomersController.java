package edu.midlands.training.controllers;

import static edu.midlands.training.constants.StringConstants.CONTEXT_CUSTOMERS;
import static edu.midlands.training.constants.StringConstants.LOGGER_DELETE_REQUEST_RECEIVED;
import static edu.midlands.training.constants.StringConstants.LOGGER_POST_REQUEST_RECEIVED;
import static edu.midlands.training.constants.StringConstants.LOGGER_PUT_REQUEST_RECEIVED;
import static edu.midlands.training.constants.StringConstants.LOGGER_REQUEST_RECEIVED;

import edu.midlands.training.entities.Customers;
import edu.midlands.training.services.CustomersService;
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

  @PostMapping
  public ResponseEntity<Customers> save(@Valid @RequestBody Customers customer) {
    logger.info(new Date() + LOGGER_POST_REQUEST_RECEIVED);

    return new ResponseEntity<>(customersService.addCustomer(customer), HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Customers> updateCustomerById(
      @PathVariable Long id, @Valid @RequestBody Customers customer) {
    logger.info(new Date() + LOGGER_PUT_REQUEST_RECEIVED + id);

    return new ResponseEntity<>(customersService.updateCustomerById(customer,id), HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCustomer(@PathVariable Long id) {
    logger.info(new Date() + LOGGER_DELETE_REQUEST_RECEIVED + id);

    customersService.deleteCustomer(id);
  }
}
