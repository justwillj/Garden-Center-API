package edu.midlands.training.services;

import edu.midlands.training.entities.Order;
import edu.midlands.training.entities.Item;

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

}
