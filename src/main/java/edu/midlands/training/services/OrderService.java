package edu.midlands.training.services;

import edu.midlands.training.entities.Order;
import java.util.List;

public interface OrderService {

  List<Order> queryOrders(Order order);

  Order getOrder(Long id);

  Order addOrder(Order order);

  Order updateOderById(Order order, Long id);
}
