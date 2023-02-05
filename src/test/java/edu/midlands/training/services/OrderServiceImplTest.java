package edu.midlands.training.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.when;

import edu.midlands.training.entities.Item;
import edu.midlands.training.entities.Order;
import edu.midlands.training.entities.User;
import edu.midlands.training.exceptions.ServiceUnavailable;
import edu.midlands.training.repositories.OrderRepository;
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


  List<Order> testList = new ArrayList<>();

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    testOrder1 = new Order(2L, LocalDate.of(2020,01,02),new BigDecimal("150.00"),item1);
    testOrder2 = new Order(1L, LocalDate.of(2021,05,14),new BigDecimal("222.33"),item2);
    testOrder3 = new Order(3L, LocalDate.of(2022,07,22),new BigDecimal("89.45"),item3);

    item1=new Item(1L,6);
    item2=new Item(2L,10);


    testOrder1.setId(1L);
    testOrder2.setId(2L);
    testOrder3.setId(3L);
    //testUser4.setId(4L);

    testList.add(testOrder1);
    testList.add(testOrder3);

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

}