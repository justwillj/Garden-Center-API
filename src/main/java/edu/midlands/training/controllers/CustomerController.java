package edu.midlands.training.controllers;

import static edu.midlands.training.constants.StringConstants.CONTEXT_CUSTOMERS;
import static edu.midlands.training.constants.StringConstants.LOGGER_DELETE_REQUEST_RECEIVED;
import static edu.midlands.training.constants.StringConstants.LOGGER_POST_REQUEST_RECEIVED;
import static edu.midlands.training.constants.StringConstants.LOGGER_PUT_REQUEST_RECEIVED;
import static edu.midlands.training.constants.StringConstants.LOGGER_REQUEST_RECEIVED;

import edu.midlands.training.entities.Customer;
import edu.midlands.training.services.CustomerService;
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
 * This controller has all the CRUD methods for the customer entity
 */
@RestController
@RequestMapping(CONTEXT_CUSTOMERS)
public class CustomerController {

  private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

  @Autowired
  private CustomerService customerService;

  /**
   * give you all the customers if you pass a null user or user matching an example with non-null customer
   *
   * @param customer object which can have null or non-null fields, returns status 200
   * @return List of customers
   */
  @GetMapping
  public ResponseEntity<List<Customer>> queryCustomers(Customer customer) {
    logger.info(new Date() + LOGGER_REQUEST_RECEIVED + customer.toString());

    return new ResponseEntity<>(customerService.queryCustomers(customer), HttpStatus.OK);
  }

  /**
   * Gets customer by id.
   *
   * @param id the customer's id from the path variable
   * @return the Customer with said id
   */
  @GetMapping(value = "/{id}")
  public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
    logger.info(new Date() + LOGGER_REQUEST_RECEIVED + id);

    return new ResponseEntity<>(customerService.getCustomer(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Customer> save(@Valid @RequestBody Customer customer) {
    logger.info(new Date() + LOGGER_POST_REQUEST_RECEIVED);

    return new ResponseEntity<>(customerService.addCustomer(customer), HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Customer> updateCustomerById(
      @PathVariable Long id, @Valid @RequestBody Customer customer) {
    logger.info(new Date() + LOGGER_PUT_REQUEST_RECEIVED + id);

    return new ResponseEntity<>(customerService.updateCustomerById(customer,id), HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCustomer(@PathVariable Long id) {
    logger.info(new Date() + LOGGER_DELETE_REQUEST_RECEIVED + id);

    customerService.deleteCustomer(id);
  }
}
