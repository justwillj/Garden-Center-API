package edu.midlands.training.services;


import edu.midlands.training.entities.Customers;
import edu.midlands.training.entities.Users;
import edu.midlands.training.exceptions.ServiceUnavailable;
import edu.midlands.training.repositories.CustomersRepository;
import edu.midlands.training.repositories.UsersRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class CustomersServiceImpl implements CustomersService {

  @Autowired
  private CustomersRepository customersRepository;

  @Autowired
  private UsersRepository usersRepository;
  @Override
  public List<Customers> queryCustomers(Customers customers) {
    try {
      if (customers.isEmpty()) {
        return customersRepository.findAll();
      } else {
        Example<Customers> customersExample = Example.of(customers);
        return customersRepository.findAll(customersExample);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }
}
