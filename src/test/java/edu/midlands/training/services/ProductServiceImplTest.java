package edu.midlands.training.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import edu.midlands.training.entities.Product;
import edu.midlands.training.entities.User;
import edu.midlands.training.exceptions.ResourceNotFound;
import edu.midlands.training.exceptions.ServiceUnavailable;
import edu.midlands.training.repositories.ProductRepository;
import edu.midlands.training.repositories.UserRepository;
import java.math.BigDecimal;
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

class ProductServiceImplTest {
  @Mock
  ProductRepository productRepository;

  @InjectMocks
  ProductServiceImpl productServiceImpl;

  Product testProduct1;
  Product testProduct2;
  Product testProduct3;
  List<Product> testList = new ArrayList<>();

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    testProduct1= new Product("TS12356","Shoes","Leather Platform","Really cool shoes!","Dr. Martens",new BigDecimal(
        "26.10"));
    testProduct2= new Product("TS12356","Shoes","Leather Platform","Really cool shoes!","Dr. Martens",new BigDecimal(
        "26.10"));
    testProduct3= new Product("TS12356","Shoes","Leather Platform","Really cool shoes!","Dr. Martens",new BigDecimal(
        "26.10"));
    testProduct1.setId(1L);
    testProduct2.setId(2L);
    testProduct3.setId(3L);

    testList.add(testProduct1);
    testList.add(testProduct3);

    when(productRepository.findAll()).thenReturn(testList);
    when(productRepository.findAll(any(Example.class))).thenReturn(testList);
    when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(testList.get(0)));
    when(productRepository.save(any(Product.class))).thenReturn(testList.get(0));
    when(productRepository.saveAll(anyCollection())).thenReturn(testList);

  }

  @Test
  void queryAllProduct() {
    List<Product> results = productServiceImpl.queryProducts(new Product());
    assertEquals(testList,results);
  }

  @Test
  void queryAllProductWithSample() {
    List<Product> results = productServiceImpl.queryProducts(testProduct1);
    assertEquals(testList,results);
  }

  @Test
  void queryAllProductsDBError() {
    when(productRepository.findAll()).thenThrow(EmptyResultDataAccessException.class);

    assertThrows(ServiceUnavailable.class,
        () -> productServiceImpl.queryProducts(new Product()));
  }

  @Test
  public void getProduct() {
    Product result = productServiceImpl.getProduct(1L);
    assertEquals(testProduct1, result);
  }

  @Test
  public void getProductDBError() {
    when(productRepository.findById(anyLong())).thenThrow(EmptyResultDataAccessException.class);
    assertThrows(ServiceUnavailable.class,
        () -> productServiceImpl.getProduct(1L));
  }

  @Test
  public void getProductNotFound() {
    when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
    Exception exception = assertThrows(ResourceNotFound.class,
        () -> productServiceImpl.getProduct(1L));
    String expectedMessage = "Could not locate a Product with the id: 1";
    assertEquals(expectedMessage,
        exception.getMessage(),
        () -> "Message did not equal '" + expectedMessage + "', actual message:"
            + exception.getMessage());
  }
}