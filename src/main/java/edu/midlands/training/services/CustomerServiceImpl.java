package edu.midlands.training.services;


import edu.midlands.training.entities.Customer;
import edu.midlands.training.exceptions.BadDataResponse;
import edu.midlands.training.exceptions.ConflictData;
import edu.midlands.training.exceptions.ResourceNotFound;
import edu.midlands.training.exceptions.ServiceUnavailable;
import edu.midlands.training.repositories.CustomerRepository;
import edu.midlands.training.repositories.UserRepository;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private UserRepository userRepository;

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
      throw new ServiceUnavailable(e);
    }
  }

  @Override
  public Customer getCustomer(Long id) {
    try {
      Customer customer = customerRepository.findById(id).orElse(null);

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
  public Customer addCustomer(Customer customer) {

    for (Customer c: customerRepository.findAll()){

      if (Objects.equals(c.getEmail().toLowerCase(), customer.getEmail().toLowerCase())){
        throw new ConflictData("This email is already in use!");
      }
    }

    try {
      return customerRepository.save(customer);
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  @Override
  public Customer updateCustomerById(Customer customer, Long id) {
    // first, check to make sure the id passed matches the id in the Pet passed
    if (!customer.getId().equals(id)) {
      throw new BadDataResponse("Customer ID must match the ID specified in the URL");
    }

    for (Customer c: customerRepository.findAll()) {
      if (Objects.equals(customer.getId(), c.getId()) && Objects.equals(customer.getEmail(),
          c.getEmail())) {
        return customerRepository.save(customer);
      }
      if (Objects.equals(c.getEmail().toLowerCase(), customer.getEmail().toLowerCase())) {
        throw new ConflictData("This email is already in use!");
      }
    }
    try {
      Customer customerFromDb = customerRepository.findById(id).orElse(null);
      if (customerFromDb != null) {
        return customerRepository.save(customer);
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
      if (customerRepository.existsById(id)) {
        customerRepository.deleteById(id);
        return;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this pint, we did not find the Pet
    throw new ResourceNotFound("Could not locate a Customer with the id: " + id);
  }


}
