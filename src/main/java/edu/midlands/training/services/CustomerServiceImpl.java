package edu.midlands.training.services;


import edu.midlands.training.entities.Customer;
import edu.midlands.training.exceptions.BadDataResponse;
import edu.midlands.training.exceptions.ConflictData;
import edu.midlands.training.exceptions.ResourceNotFound;
import edu.midlands.training.exceptions.ServiceUnavailable;
import edu.midlands.training.repositories.CustomerRepository;
import edu.midlands.training.repositories.UserRepository;
import io.netty.util.concurrent.BlockingOperationException;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

  private final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);


  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private UserRepository userRepository;

  /**
   * This method will take a customer as an optional parameter. If the customer is given then it
   * will create a query by example. If nothing is given then we will get all customers.
   *
   * @param customer - any provided fields will be converted to an exact match AND queried
   * @return a list of customers that match the query, if not supplied then all the customers in the
   * database
   */
  @Override
  public List<Customer> queryCustomers(Customer customer) {
    try {
      if (customer.isEmpty()) {
        return customerRepository.findAll();
      } else {
        Example<Customer> customersExample = Example.of(customer);
        // Example<Address> addressExample = Example.of(address);
        return customerRepository.findAll(customersExample);
      }
    } catch (Exception e) {
      logger.error("Could not get customers" + e.getMessage());
      throw new ServiceUnavailable(e);
    }
  }


  /**
   * Lookup a Customer by its id.
   *
   * @param id - the id to lookup
   * @return the customer that matches the id
   */
  @Override
  public Customer getCustomer(Long id) {
    try {
      Customer customer = customerRepository.findById(id).orElse(null);

      if (customer != null) {
        return customer;
      }
    } catch (Exception e) {
      logger.error("Could not get customer" + e.getMessage());
      throw new ServiceUnavailable(e);
    }
    // if we made it down to this pint, we did not find the Customer
    logger.error("Could not locate a Customer with the id: " + id);
    throw new ResourceNotFound("Could not locate a Customer with the id: " + id);
  }

  /**
   * Adds a new Customer to the database.
   *
   * @param customer - the customer that will be added to the database.
   * @return the new customer if the required fields are inputted correctly
   */
  @Override
  public Customer addCustomer(Customer customer) {

    //Checks to see if this email is already being used
    for (Customer c : customerRepository.findAll()) {
      if (Objects.equals(c.getEmail().toLowerCase(), customer.getEmail().toLowerCase())) {
        logger.error("This email is already in use!");
        throw new ConflictData("This email is already in use!");
      }
    }

    try {
      return customerRepository.save(customer);
    } catch (Exception e) {
      logger.error("Could not add customer" + e.getMessage());
      throw new ServiceUnavailable(e);
    }
  }

  /**
   * Update an existing Customer in the database.
   *
   * @param id       - the id of the customer to update.
   * @param customer - the Customer information to update.
   * @return the updated customer if done correctly
   */
  @Override
  public Customer updateCustomerById(Customer customer, Long id) {
    // first, check to make sure the id passed matches the id in the Pet passed
    if (!customer.getId().equals(id)) {
      logger.error("Customer ID must match the ID specified in the URL");
      throw new BadDataResponse("Customer ID must match the ID specified in the URL");
    }

    //Allows the customer to keep there current email while updating
    for (Customer c : customerRepository.findAll()) {
      if (Objects.equals(customer.getId(), c.getId()) && Objects.equals(customer.getEmail(),
          c.getEmail())) {
        return customerRepository.save(customer);
      }
      //Checks to see if this email is already in use
      if (Objects.equals(c.getEmail().toLowerCase(), customer.getEmail().toLowerCase())) {
        logger.error("This email is already in use!");
        throw new ConflictData("This email is already in use!");
      }
    }
    try {
      Customer customerFromDb = customerRepository.findById(id).orElse(null);
      if (customerFromDb != null) {
        return customerRepository.save(customer);
      }
    } catch (Exception e) {
      logger.error("Could not update customer" + e.getMessage());
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this pint, we did not find the Customer
    logger.error("Could not locate a Customer with the id: " + id);
    throw new ResourceNotFound("Could not locate a Customer with the id: " + id);
  }

  /**
   * Delete a Customer from the database.
   *
   * @param id - the id of the customer to be deleted.
   */
  @Override
  public void deleteCustomer(Long id) {
    try {
      if (customerRepository.existsById(id)) {
        customerRepository.deleteById(id);
        return;
      }
    } catch (Exception e) {
      logger.error("Could not delete customer" + e.getMessage());
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this pint, we did not find the Customer
    logger.error("Could not locate a Customer with the id: " + id);
    throw new ResourceNotFound("Could not locate a Customer with the id: " + id);
  }


}
