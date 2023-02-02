package edu.midlands.training.controllers;

import static edu.midlands.training.constants.StringConstants.CONTEXT_CUSTOMERS;
import static edu.midlands.training.constants.StringConstants.CONTEXT_PRODUCTS;
import static edu.midlands.training.constants.StringConstants.LOGGER_REQUEST_RECEIVED;

import edu.midlands.training.entities.Customer;
import edu.midlands.training.entities.Product;
import edu.midlands.training.services.CustomerService;
import edu.midlands.training.services.ProductService;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CONTEXT_PRODUCTS)
public class ProductController {

  private final Logger logger = LoggerFactory.getLogger(ProductController.class);

  @Autowired
  private ProductService productService;

  /**
   * give you all the products if you pass a null user or user matching an example with non-null product
   *
   * @param product object which can have null or non-null fields, returns status 200
   * @return List of products
   */
  @GetMapping
  public ResponseEntity<List<Product>> queryProducts(Product product) {
    logger.info(new Date() + LOGGER_REQUEST_RECEIVED + product.toString());

    return new ResponseEntity<>(productService.queryProducts(product), HttpStatus.OK);
  }


}
