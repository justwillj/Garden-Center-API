package edu.midlands.training.services;


import edu.midlands.training.entities.Customer;
import edu.midlands.training.entities.Product;
import edu.midlands.training.entities.User;
import java.util.List;

public interface ProductService {

  List<Product> queryProducts(Product product);

  Product getProduct(Long id);

  Product addProduct(Product product);
}
