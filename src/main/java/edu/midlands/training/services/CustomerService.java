package edu.midlands.training.services;

import edu.midlands.training.entities.Customer;
import java.util.List;

public interface CustomerService {

  List<Customer> queryCustomers(Customer customer);

  Customer getCustomer(Long id);

  Customer addCustomer(Customer customer);

  Customer updateCustomerById(Customer customer, Long id);

  void deleteCustomer(Long id);

}
