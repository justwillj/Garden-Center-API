package edu.midlands.training.services;


import edu.midlands.training.entities.Product;
import java.util.List;

public interface ProductService {

  List<Product> queryProducts(Product product);

}
