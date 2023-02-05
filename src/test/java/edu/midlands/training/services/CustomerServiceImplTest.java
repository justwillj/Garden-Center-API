package edu.midlands.training.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import edu.midlands.training.entities.Address;
import edu.midlands.training.entities.Customer;
import edu.midlands.training.entities.User;
import edu.midlands.training.exceptions.BadDataResponse;
import edu.midlands.training.exceptions.ConflictData;
import edu.midlands.training.exceptions.ResourceNotFound;
import edu.midlands.training.exceptions.ServiceUnavailable;
import edu.midlands.training.repositories.AddressRepository;
import edu.midlands.training.repositories.CustomerRepository;
import edu.midlands.training.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;

class CustomerServiceImplTest {


  @Mock
  CustomerRepository customerRepository;

  @Mock
  AddressRepository addressRepository;

  @InjectMocks
  CustomerServiceImpl customerServiceImpl;

  Customer testCustomer1;
  Customer testCustomer2;
  Customer testCustomer3;

  Address testAddress1;
  Address testAddress2;
  List<Customer> testList = new ArrayList<>();

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    testCustomer1 = new Customer("John","john@gmail.com",testAddress1);
    testCustomer2 = new Customer("Jake","jake@gmail.com",testAddress2);
    testCustomer3 = new Customer("John","john@gmail.com",testAddress1);
    testAddress1 = new Address("1169 Boone Crockett Lane","Olympia","WA","98501");
    testAddress2 = new Address("4021 Cedar Street","Batesville","AR","72501-1234");

    testCustomer1.setId(1L);
    testCustomer2.setId(2L);
    testCustomer3.setId(3L);

    testList.add(testCustomer1);
    testList.add(testCustomer3);

    when(customerRepository.findAll()).thenReturn(testList);
    when(customerRepository.findAll(any(Example.class))).thenReturn(testList);
    when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(testList.get(0)));
    when(customerRepository.save(any(Customer.class))).thenReturn(testList.get(0));
    when(customerRepository.saveAll(anyCollection())).thenReturn(testList);

  }

  @Test
  void queryAllCustomers() {
    List<Customer> result = customerServiceImpl.queryCustomers(new Customer());
    assertEquals(testList, result);
  }

  @Test
  void queryAllCustomerWithSample() {
    List<Customer> results = customerServiceImpl.queryCustomers(testCustomer1);
    assertEquals(testList,results);
  }


  @Test
  void queryAllCustomerDBError() {
    when(customerRepository.findAll()).thenThrow(EmptyResultDataAccessException.class);

    assertThrows(ServiceUnavailable.class,
        () -> customerServiceImpl.queryCustomers(new Customer()));
  }

  @Test
  public void getCustomer() {
    Customer result = customerServiceImpl.getCustomer(1L);
    assertEquals(testCustomer1, result);
  }

  @Test
  public void getCustomerDBError() {
    when(customerRepository.findById(anyLong())).thenThrow(EmptyResultDataAccessException.class);
    assertThrows(ServiceUnavailable.class,
        () -> customerServiceImpl.getCustomer(1L));
  }

  @Test
  public void getCustomerNotFound() {
    when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
    Exception exception = assertThrows(ResourceNotFound.class,
        () -> customerServiceImpl.getCustomer(1L));
    String expectedMessage = "Could not locate a Customer with the id: 1";
    assertEquals(expectedMessage,
        exception.getMessage(),
        () -> "Message did not equal '" + expectedMessage + "', actual message:"
            + exception.getMessage());
  }

  @Test
  public void addCustomer() {
    Customer result = customerServiceImpl.addCustomer(testCustomer2);
    assertEquals(testCustomer1, result);
  }

  @Test
  public void addCustomerDBError() {
    when(customerRepository.save(any(Customer.class))).thenThrow(
        new EmptyResultDataAccessException("Database unavailable", 0));
    assertThrows(ServiceUnavailable.class,
        () -> customerServiceImpl.addCustomer(testCustomer2));
  }

  @Test
  public void addCustomerEmailAlreadyUsed() {
    when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
    Exception exception = assertThrows(ConflictData.class,
        () -> customerServiceImpl.addCustomer(testCustomer1));
    String expectedMessage = "This email is already in use!";
    assertEquals(expectedMessage,
        exception.getMessage(),
        () -> "Message did not equal '" + expectedMessage + "', actual message:"
            + exception.getMessage());
  }
}