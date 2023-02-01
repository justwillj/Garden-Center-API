package edu.midlands.training.services;

import edu.midlands.training.entities.Address;
import edu.midlands.training.entities.Customers;
import edu.midlands.training.entities.Users;
import java.util.List;

public interface CustomersService {

  List<Customers> queryCustomers(Customers customers);

  Customers getCustomer(Long id);

  Customers addCustomer(Customers customer);

  Customers updateCustomerById(Customers customer, Long id);

}
