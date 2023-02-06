package edu.midlands.training.services;

import edu.midlands.training.entities.Customer;
import edu.midlands.training.entities.Item;
import edu.midlands.training.entities.Order;

import edu.midlands.training.entities.Product;
import edu.midlands.training.exceptions.BadDataResponse;
import edu.midlands.training.exceptions.ResourceNotFound;
import edu.midlands.training.exceptions.ServiceUnavailable;
import edu.midlands.training.repositories.CustomerRepository;
import edu.midlands.training.repositories.ItemRepository;
import edu.midlands.training.repositories.OrderRepository;
import edu.midlands.training.repositories.ProductRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private CustomerRepository customerRepository;


  /**
   * This method will take an order as an optional parameter. If the user is given then it will
   * create a query by example. If nothing is given then we will get all orders.
   *
   * @param order - any provided fields will be converted to an exact match AND queried
   * @return a list of orders that match the query, if not supplied then all the orders in the
   * database
   */
  @Override
  public List<Order> queryOrders(Order order) {
    try {
      if (order.isEmpty()) {
        return orderRepository.findAll();
      } else {
        Example<Order> orderExample = Example.of(order);
        return orderRepository.findAll(orderExample);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }


  /**
   * Lookup an Order by its id.
   *
   * @param id - the id to lookup
   * @return the order that matches the id
   */
  @Override
  public Order getOrder(Long id) {
    try {
      Order order = orderRepository.findById(id).orElse(null);

      if (order != null) {
        return order;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
    // if we made it down to this pint, we did not find the Order
    throw new ResourceNotFound("Could not locate a Order with the id: " + id);
  }


  /**
   * Adds a new Order to the database.
   *
   * @param order - the order that will be added to the database.
   * @return the new order if the required fields are inputted correctly
   */
  @Override
  public Order addOrder(Order order) {
    BigDecimal rounded = order.getOrderTotal().setScale(2, RoundingMode.CEILING);
    order.setOrderTotal(rounded);

    boolean customerTest = false;
    boolean productTest = false;

    for (Customer c : customerRepository.findAll()) {
      if (Objects.equals(order.getCustomerId(), c.getId())) {
        customerTest = true;
        break;
      }
    }

    for (Product p : productRepository.findAll()) {
      if (Objects.equals(order.getItems().getProductId(), p.getId())) {
        productTest = true;
        break;
      }


    }
    if (!customerTest) {
      throw new BadDataResponse("This customer Id is not in the system!");
    }
    if (!productTest) {
      throw new BadDataResponse("This product Id is not in the system");
    }

    if (order.getItems().getQuantity() < 0) {
      throw new BadDataResponse("Quantity must be greater then 0!");
    }
    try {
      return orderRepository.save(order);
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

  }


  /**
   * Update an existing Order in the database.
   *
   * @param id    - the id of the order to update.
   * @param order - the Order information to update.
   * @return the updated order if done correctly
   */
  @Override
  public Order updateOrderById(Order order, Long id) {
    // first, check to make sure the id passed matches the id in the Pet passed
    if (!Objects.equals(order.getId(), id)) {
      throw new BadDataResponse("Order ID must match the ID specified in the URL");
    }
    BigDecimal rounded = order.getOrderTotal().setScale(2, RoundingMode.CEILING);
    order.setOrderTotal(rounded);
    System.out.println(order);
    System.out.println(order.getItems());

    boolean customerTest = false;
    boolean productTest = false;

    for (Customer c : customerRepository.findAll()) {
      if (Objects.equals(order.getCustomerId(), c.getId())) {
        customerTest = true;
        break;
      }
    }
    for (Product p : productRepository.findAll()) {
      if (Objects.equals(order.getItems().getId(), p.getId())) {
        productTest = true;
        break;
      }
    }
    if (!customerTest) {
      throw new BadDataResponse("This customer Id is not in the system!");
    }
    if (!productTest) {
      throw new BadDataResponse("This product Id is not in the system");
    }

    if (order.getItems().getQuantity() < 0) {
      throw new BadDataResponse("Quantity must be greater then 0!");
    }

    try {
      Order orderFromDb = orderRepository.findById(id).orElse(null);
      if (orderFromDb != null) {
        return orderRepository.save(order);
      }

    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
    // if we made it down to this pint, we did not find the User
    throw new ResourceNotFound("Could not locate a Order with the id: " + id);
  }


  /**
   * Delete an Order from the database.
   *
   * @param id - the id of the order to be deleted.
   */
  @Override
  public void deleteOrder(Long id) {
    try {
      if (orderRepository.existsById(id)) {
        orderRepository.deleteById(id);
        return;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this pint, we did not find the Pet
    throw new ResourceNotFound("Could not locate a Order with the id: " + id);
  }
}
