package edu.midlands.training.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.midlands.training.entities.Address;
import edu.midlands.training.entities.Customer;
import edu.midlands.training.entities.Item;
import edu.midlands.training.entities.Order;
import edu.midlands.training.entities.Product;
import edu.midlands.training.entities.User;
import edu.midlands.training.exceptions.BadDataResponse;
import edu.midlands.training.exceptions.ConflictData;
import edu.midlands.training.exceptions.ResourceNotFound;
import edu.midlands.training.exceptions.ServiceUnavailable;
import edu.midlands.training.repositories.AddressRepository;
import edu.midlands.training.repositories.CustomerRepository;
import edu.midlands.training.repositories.ItemRepository;
import edu.midlands.training.repositories.OrderRepository;
import edu.midlands.training.repositories.ProductRepository;
import edu.midlands.training.repositories.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;

class OrderServiceImplTest {


  @Mock
  OrderRepository orderRepository;
  @Mock
  CustomerRepository customerRepository;
  @Mock
  ProductRepository productRepository;
  @Mock
  AddressRepository addressRepository;
  @Mock
  ItemRepository itemRepository;


  @InjectMocks
  OrderServiceImpl orderServiceImpl;

  Order testOrder1;
  Order testOrder2;
  Order testOrder3;
  Order testOrder4;

  Order testOrder5;

  Item item1;
  Item item2;
  Item item3;

  Customer customer1;
  Customer customer2;


  Address address1;

  Product product1;


  List<Order> testList = new ArrayList<>();
  List<Customer> testCustomer = new ArrayList<>();
  List<Product> testProduct = new ArrayList<>();

  List<Item> testItem =new ArrayList<>();


  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    testOrder1 = new Order(2L, LocalDate.of(2020,01,02),new BigDecimal("150.00"),item1);
    testOrder2 = new Order(1L, LocalDate.of(2021,05,14),new BigDecimal("222.33"),item2);
    testOrder3 = new Order(3L, LocalDate.of(2022,07,22),new BigDecimal("89.45"),item3);

    item1=new Item(1L,6);
    item2=new Item(1L,10);

    address1 = new Address("1169 Boone Crockett Lane","Olympia","WA","98501");

    customer1 = new Customer("John","john@gmail.com",address1);
    customer2 = new Customer("John","john@gmail.com",address1);

    product1= new Product("TS12356","Shoes","Leather Platform","Really cool shoes!","Dr. Martens",new BigDecimal(
        "26.10"));

    customer1.setId(1L);
    customer2.setId(2L);
    testCustomer.add(customer1);

    product1.setId(1L);
    testProduct.add(product1);

    testItem.add(item1);
    testItem.add(item2);


    testOrder1.setId(1L);
    testOrder2.setId(2L);
    testOrder3.setId(3L);
    //testUser4.setId(4L);

    testList.add(testOrder1);
    testList.add(testOrder3);



    when(productRepository.findAll()).thenReturn(testProduct);
    when(customerRepository.findAll()).thenReturn(testCustomer);

    when(itemRepository.findAll()).thenReturn(testItem);
    when(itemRepository.findAll(any(Example.class))).thenReturn(testItem);
    when(itemRepository.findById(any(Long.class))).thenReturn(Optional.of(testItem.get(0)));

    when(orderRepository.findAll()).thenReturn(testList);
    when(orderRepository.findAll(any(Example.class))).thenReturn(testList);
    when(orderRepository.findById(any(Long.class))).thenReturn(Optional.of(testList.get(0)));
    when(orderRepository.save(any(Order.class))).thenReturn(testList.get(0));
    when(orderRepository.saveAll(anyCollection())).thenReturn(testList);

  }

  @Test
  void queryAllOrders() {
    List<Order> results = orderServiceImpl.queryOrders(new Order());
    assertEquals(testList,results);
  }

  @Test
  void queryAllOrdersWithSample() {
    List<Order> results = orderServiceImpl.queryOrders(testOrder1);
    assertEquals(testList,results);
  }

  @Test
  void queryAllOrdersDBError() {
    when(orderRepository.findAll()).thenThrow(EmptyResultDataAccessException.class);

    assertThrows(ServiceUnavailable.class,
        () -> orderServiceImpl.queryOrders(new Order()));
  }

  @Test
  public void getOrder() {
    Order result = orderServiceImpl.getOrder(1L);
    assertEquals(testOrder1, result);
  }

  @Test
  public void getOrderDBError() {
    when(orderRepository.findById(anyLong())).thenThrow(EmptyResultDataAccessException.class);
    assertThrows(ServiceUnavailable.class,
        () -> orderServiceImpl.getOrder(1L));
  }

  @Test
  public void getOrderNotFound() {
    when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
    Exception exception = assertThrows(ResourceNotFound.class,
        () -> orderServiceImpl.getOrder(1L));
    String expectedMessage = "Could not locate a Order with the id: 1";
    assertEquals(expectedMessage,
        exception.getMessage(),
        () -> "Message did not equal '" + expectedMessage + "', actual message:"
            + exception.getMessage());
  }

  @Test
  public void addOrder() {
    Order result = orderServiceImpl.addOrder(testOrder2);
    assertEquals(testOrder1, result);
  }

  @Test
  public void addOrderDBError() {
    when(orderRepository.save(any(Order.class))).thenThrow(
        new EmptyResultDataAccessException("Database unavailable", 0));
    assertThrows(ServiceUnavailable.class,
        () -> orderServiceImpl.addOrder(testOrder2));
  }

  @Test
  public void addOrderCustomerIdNotInSystem() {
    when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
    Exception exception = assertThrows(BadDataResponse.class,
        () -> orderServiceImpl.addOrder(testOrder3));
    String expectedMessage = "This customer Id is not in the system!";
    assertEquals(expectedMessage,
        exception.getMessage(),
        () -> "Message did not equal '" + expectedMessage + "', actual message:"
            + exception.getMessage());
  }



  @Test
  public void deleteOrder() {
    when(orderRepository.existsById(anyLong())).thenReturn(true);
    orderServiceImpl.deleteOrder(1L);
    verify(orderRepository).deleteById(any());
  }

  @Test
  public void deleteOrderBadID() {
    doThrow(new ResourceNotFound("Database unavailable")).when(orderRepository)
        .deleteById(anyLong());
    assertThrows(ResourceNotFound.class,
        () -> orderServiceImpl.deleteOrder(1L));
  }

  @Test
  public void deleteOrderDBError() {
    doThrow(new ServiceUnavailable("Database unavailable")).when(orderRepository)
        .existsById(anyLong());
    assertThrows(ServiceUnavailable.class,
        () -> orderServiceImpl.deleteOrder(1L));
  }

}