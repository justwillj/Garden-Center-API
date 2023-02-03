package edu.midlands.training.services;

import edu.midlands.training.entities.Order;
import edu.midlands.training.entities.User;
import java.util.List;

public interface OrderService {

  List<Order> queryOrders(Order order);
}
