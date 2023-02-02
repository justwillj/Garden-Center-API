package edu.midlands.training.services;

import edu.midlands.training.entities.Product;
import edu.midlands.training.exceptions.ConflictData;
import edu.midlands.training.exceptions.ResourceNotFound;
import edu.midlands.training.exceptions.ServiceUnavailable;
import edu.midlands.training.repositories.ProductRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
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

  /**
   * Adds a new Product to the database.
   *
   * @param product - the user that will be added to the database.
   * @return the new product if the required fields are inputted correctly
   */
  @Override
  public Product addProduct(Product product) {
    BigDecimal rounded = product.getPrice().setScale(2, RoundingMode.CEILING);
    product.setPrice(rounded);

    for (Product p: productRepository.findAll()){
      if (Objects.equals(p.getSku(), product.getSku())){
        throw new ConflictData("This email is already in use!");
      }
    }

    try {
      return productRepository.save(product);
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }
}
