package edu.midlands.training.services;

import edu.midlands.training.entities.Customers;
import edu.midlands.training.entities.Users;
import java.util.List;

public interface CustomersService {

  List<Customers> queryCustomers(Customers customers);


}
