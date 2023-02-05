package edu.midlands.training.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.midlands.training.entities.Product;
import edu.midlands.training.entities.User;
import edu.midlands.training.exceptions.BadDataResponse;
import edu.midlands.training.exceptions.ConflictData;
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
  Product testProduct4;
  List<Product> testList = new ArrayList<>();

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    testProduct1= new Product("TS17832","Shoes","Leather Platform","Really cool shoes!","Dr. Martens",new BigDecimal(
        "26.10"));
    testProduct2= new Product("WA-612436","Shoes","Leather Platform","Really cool shoes!","Dr. Martens",new BigDecimal(
        "26.10"));
    testProduct3= new Product("BAZ124483","Shoes","Leather Platform","Really cool shoes!","Dr. Martens",new BigDecimal(
        "26.10"));
    testProduct4= new Product("BAZ124483","Shoes","Leather Platform","Really cool shoes!","Dr. Martens",new BigDecimal(
        "26.10"));
    testProduct1.setId(1L);
    testProduct2.setId(2L);
    testProduct3.setId(3L);
    testProduct4.setId(4L);

    testList.add(testProduct1);
    testList.add(testProduct3);
    testList.add(testProduct4);

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

  @Test
  public void addProduct() {
    Product result = productServiceImpl.addProduct(testProduct2);
    assertEquals(testProduct1, result);
  }

  @Test
  public void addProductDBError() {
    when(productRepository.save(any(Product.class))).thenThrow(
        new EmptyResultDataAccessException("Database unavailable", 0));
    assertThrows(ServiceUnavailable.class,
        () -> productServiceImpl.addProduct(testProduct2));
  }

  @Test
  public void addProductSkuAlreadyUsed() {
    when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
    Exception exception = assertThrows(ConflictData.class,
        () -> productServiceImpl.addProduct(testProduct1));
    String expectedMessage = "This sku is already in use!";
    assertEquals(expectedMessage,
        exception.getMessage(),
        () -> "Message did not equal '" + expectedMessage + "', actual message:"
            + exception.getMessage());
  }

  @Test
  public void updateProductById() {
    Product result = productServiceImpl.updateProductById(testProduct1, 1L);
    assertEquals(testProduct1, result);
  }

  @Test
  public void updatePByIdDBError() {
    when(productRepository.findById(anyLong())).thenThrow(EmptyResultDataAccessException.class);
    assertThrows(ServiceUnavailable.class,
        () -> productServiceImpl.updateProductById(testProduct2, 2L));
  }

  @Test
  public void updateProductSkuAlreadyUsed() {
    when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
    Exception exception = assertThrows(ConflictData.class,
        () -> productServiceImpl.updateProductById(testProduct4,4L).setSku("BAZ124483"));
    String expectedMessage = "This sku is already in use!";
    assertEquals(expectedMessage,
        exception.getMessage(),
        () -> "Message did not equal '" + expectedMessage + "', actual message:"
            + exception.getMessage());
  }

  @Test
  public void updateProductByIdBadData() {
    Exception exception = assertThrows(BadDataResponse.class,
        () -> productServiceImpl.updateProductById(testProduct1, 2L));
    String expectedMessage = "Product ID must match the ID specified in the URL";
    assertEquals(expectedMessage,
        exception.getMessage(),
        () -> "Message did not equal '" + expectedMessage + "', actual message:"
            + exception.getMessage());
  }

  @Test
  public void updateProductByIdNotFound() {
    when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

    Exception exception = assertThrows(ResourceNotFound.class,
        () -> productServiceImpl.updateProductById(testProduct2, 2L));
    String expectedMessage = "Could not locate a Product with the id: 2";
    assertEquals(expectedMessage,
        exception.getMessage(),
        () -> "Message did not equal '" + expectedMessage + "', actual message:"
            + exception.getMessage());
  }

  @Test
  public void deleteProduct() {
    when(productRepository.existsById(anyLong())).thenReturn(true);
   productServiceImpl.deleteProduct(1L);
    verify(productRepository).deleteById(any());
  }

  @Test
  public void deleteProductBadID() {
    doThrow(new ResourceNotFound("Database unavailable")).when(productRepository)
        .deleteById(anyLong());
    assertThrows(ResourceNotFound.class,
        () -> productServiceImpl.deleteProduct(1L));
  }

  @Test
  public void deleteProductDBError() {
    doThrow(new ServiceUnavailable("Database unavailable")).when(productRepository)
        .existsById(anyLong());
    assertThrows(ServiceUnavailable.class,
        () -> productServiceImpl.deleteProduct(1L));
  }
}