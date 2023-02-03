package edu.midlands.training.controllers;

import static edu.midlands.training.constants.StringConstants.CONTEXT_CUSTOMERS;
import static edu.midlands.training.constants.StringConstants.CONTEXT_ORDERS;
import static edu.midlands.training.constants.StringConstants.LOGGER_REQUEST_RECEIVED;

import edu.midlands.training.entities.Order;
import edu.midlands.training.entities.Product;
import edu.midlands.training.entities.User;
import edu.midlands.training.services.OrderService;
import edu.midlands.training.services.ProductService;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller has all the CRUD methods for the order entity
 */
@RestController
@RequestMapping(CONTEXT_ORDERS)
public class OrderController {

  private final Logger logger = LoggerFactory.getLogger(OrderController.class);

  @Autowired
  private OrderService orderService;

  /**
   * give you all the orders if you pass a null order or order matching an example with non-null product
   *
   * @param order object which can have null or non-null fields, returns status 200
   * @return List of orders
   */
  @GetMapping
  public ResponseEntity<List<Order>> queryOrders(Order order) {
    logger.info(new Date() + LOGGER_REQUEST_RECEIVED + order.toString());

    return new ResponseEntity<>(orderService.queryOrders(order), HttpStatus.OK);
  }


  /**
   * Gets order by id.
   *
   * @param id the order's id from the path variable
   * @return the Order with said id
   */
  @GetMapping(value = "/{id}")
  public ResponseEntity<Order> getOrder(@PathVariable Long id) {
    logger.info(new Date() + LOGGER_REQUEST_RECEIVED + id);

    return new ResponseEntity<>(orderService.getOrder(id), HttpStatus.OK);
  }


}
