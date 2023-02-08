package edu.midlands.training.controllers;

import static edu.midlands.training.constants.StringConstants.CONTEXT_CUSTOMERS;
import static edu.midlands.training.constants.StringConstants.CONTEXT_PRODUCTS;
import static edu.midlands.training.constants.StringConstants.LOGGER_DELETE_REQUEST_RECEIVED;
import static edu.midlands.training.constants.StringConstants.LOGGER_POST_REQUEST_RECEIVED;
import static edu.midlands.training.constants.StringConstants.LOGGER_PUT_REQUEST_RECEIVED;
import static edu.midlands.training.constants.StringConstants.LOGGER_REQUEST_RECEIVED;

import edu.midlands.training.entities.Customer;
import edu.midlands.training.entities.Product;
import edu.midlands.training.entities.User;
import edu.midlands.training.services.CustomerService;
import edu.midlands.training.services.ProductService;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CONTEXT_PRODUCTS)
public class ProductController {

  private final Logger logger = LoggerFactory.getLogger(ProductController.class);

  @Autowired
  private ProductService productService;

  /**
   * give you all the products if you pass a null user or user matching an example with non-null
   * product
   *
   * @param product object which can have null or non-null fields, returns status 200
   * @return List of products
   */
  @GetMapping
  public ResponseEntity<List<Product>> queryProducts(Product product) {
    logger.info(new Date() + LOGGER_REQUEST_RECEIVED + product.toString());

    return new ResponseEntity<>(productService.queryProducts(product), HttpStatus.OK);
  }


  /**
   * Gets product by id.
   *
   * @param id the product's id from the path variable
   * @return the Product with said id
   */
  @GetMapping(value = "/{id}")
  public ResponseEntity<Product> getProduct(@PathVariable Long id) {
    logger.info(new Date() + LOGGER_REQUEST_RECEIVED + id);

    return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
  }

  /**
   * Adds a new product to the database.
   *
   * @param product the user from the request body being added
   * @return the product if everything is correctly added
   */
  @PostMapping
  public ResponseEntity<Product> saveProduct(@Valid @RequestBody Product product) {
    logger.info(new Date() + LOGGER_POST_REQUEST_RECEIVED);

    return new ResponseEntity<>(productService.addProduct(product), HttpStatus.CREATED);
  }

  /**
   * Update product by id
   *
   * @param id      the id of the product to be updated from the path variable
   * @param product the product's new information from the request body
   * @return the product if input and data is correct
   */
  @PutMapping(value = "/{id}")
  public ResponseEntity<Product> updateProductById(
      @PathVariable Long id, @Valid @RequestBody Product product) {
    logger.info(new Date() + LOGGER_PUT_REQUEST_RECEIVED + id);

    return new ResponseEntity<>(productService.updateProductById(product, id), HttpStatus.OK);
  }

  /**
   * Delete product by id.
   *
   * @param id the product's id from the path variable
   */
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProduct(@PathVariable Long id) {
    logger.info(new Date() + LOGGER_DELETE_REQUEST_RECEIVED + id);

    productService.deleteProduct(id);
  }

}
