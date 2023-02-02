package edu.midlands.training.services;

import edu.midlands.training.entities.Product;
import edu.midlands.training.entities.User;
import edu.midlands.training.exceptions.ResourceNotFound;
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


  /**
   * Lookup a Product by its id.
   *
   * @param id - the id to lookup
   * @return the product that matches the id
   */
  @Override
  public Product getProduct(Long id) {
    try {
      Product product = productRepository.findById(id).orElse(null);

      if (product != null) {
        return product;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
    // if we made it down to this pint, we did not find the Pet
    throw new ResourceNotFound("Could not locate a Product with the id: " + id);
  }
}
