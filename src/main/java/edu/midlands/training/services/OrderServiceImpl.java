package edu.midlands.training.services;

import edu.midlands.training.entities.Order;
import edu.midlands.training.entities.Item;

import edu.midlands.training.entities.User;
import edu.midlands.training.exceptions.ResourceNotFound;
import edu.midlands.training.exceptions.ServiceUnavailable;
import edu.midlands.training.repositories.ItemRepository;
import edu.midlands.training.repositories.OrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ItemRepository itemRepository;


  /**
   * This method will take a order as an optional parameter. If the user is given then it will create
   * a query by example. If nothing is given then we will get all orders.
   *
   * @param order - any provided fields will be converted to an exact match AND query
   * @return a list of orders that match the query, if not supplied then all the orders in the database
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

}
