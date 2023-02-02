package edu.midlands.training.services;

import edu.midlands.training.entities.Product;
import edu.midlands.training.entities.User;
import edu.midlands.training.exceptions.ServiceUnavailable;
import edu.midlands.training.repositories.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository productRepository;


  @Override
  public List<Product> queryProducts(Product product) {
    try {
      if (product.isEmpty()) {
        return productRepository.findAll();
      } else {
        Example<Product> productExample = Example.of(product);
        return productRepository.findAll(productExample);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }
}
