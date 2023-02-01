package edu.midlands.training.services;


import edu.midlands.training.entities.Address;
import edu.midlands.training.entities.Customers;
import edu.midlands.training.entities.Users;
import edu.midlands.training.exceptions.BadDataResponse;
import edu.midlands.training.exceptions.ConflictData;
import edu.midlands.training.exceptions.ResourceNotFound;
import edu.midlands.training.exceptions.ServiceUnavailable;
import edu.midlands.training.repositories.CustomersRepository;
import edu.midlands.training.repositories.UsersRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
       // Example<Address> addressExample = Example.of(address);
        return customersRepository.findAll(customersExample);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  @Override
  public Customers getCustomer(Long id) {
    try {
      Customers customer = customersRepository.findById(id).orElse(null);

      if (customer != null) {
        return customer;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
    // if we made it down to this pint, we did not find the Pet
    throw new ResourceNotFound("Could not locate a Customer with the id: " + id);
  }

  @Override
  public Customers addCustomer(Customers customer) {

    for (Customers c: customersRepository.findAll()){

      if (Objects.equals(c.getEmail().toLowerCase(), customer.getEmail().toLowerCase())){
        throw new ConflictData("This email is already in use!");
      }
    }

    try {
      return customersRepository.save(customer);
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  @Override
  public Customers updateCustomerById(Customers customer, Long id) {
    // first, check to make sure the id passed matches the id in the Pet passed
    if (!customer.getId().equals(id)) {
      throw new BadDataResponse("Customer ID must match the ID specified in the URL");
    }

    for (Customers c: customersRepository.findAll()) {
      if (Objects.equals(customer.getId(), c.getId()) && Objects.equals(customer.getEmail(),
          c.getEmail())) {
        return customersRepository.save(customer);
      }
      if (Objects.equals(c.getEmail().toLowerCase(), customer.getEmail().toLowerCase())) {
        throw new ConflictData("This email is already in use!");
      }
    }
    try {
      Customers customerFromDb = customersRepository.findById(id).orElse(null);
      if (customerFromDb != null) {
        return customersRepository.save(customer);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this pint, we did not find the Pet
    throw new ResourceNotFound("Could not locate a Customer with the id: " + id);
  }

  @Override
  public void deleteCustomer(Long id) {
    try {
      if (customersRepository.existsById(id)) {
        customersRepository.deleteById(id);
        return;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this pint, we did not find the Pet
    throw new ResourceNotFound("Could not locate a Customer with the id: " + id);
  }


}
