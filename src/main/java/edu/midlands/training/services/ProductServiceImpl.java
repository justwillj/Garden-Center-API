package edu.midlands.training.services;

import edu.midlands.training.entities.Product;
import edu.midlands.training.entities.User;
import edu.midlands.training.exceptions.BadDataResponse;
import edu.midlands.training.exceptions.ConflictData;
import edu.midlands.training.exceptions.ResourceNotFound;
import edu.midlands.training.exceptions.ServiceUnavailable;
import edu.midlands.training.repositories.ProductRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
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
        throw new ConflictData("This sku is already in use!");
      }
    }

    try {
      return productRepository.save(product);
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  /**
   * Update an existing Product in the database.
   *
   * @param id  - the id of the product to update.
   * @param product - the Product information to update.
   * @return the updated product if done correctly
   */
  @Override
  public Product updateProductById(Product product, Long id) {

      // first, check to make sure the id passed matches the id in the Pet passed
      if (!product.getId().equals(id)) {
        throw new BadDataResponse("Product ID must match the ID specified in the URL");
      }

      //If the id of the user we are updating and the endpoint id match, allows the user to keep its
      //current email when updating
      for (Product p: productRepository.findAll()){
        if (Objects.equals(product.getId(), p.getId()) && Objects.equals(product.getSku(), p.getSku())){
          return productRepository.save(product);
        }
        //Checks to see if the email is already taken and if so throws an exceptions
        if (Objects.equals(p.getSku(), product.getSku())){
          throw new ConflictData("This sku is already in use!");
        }
      }
      try {
        Product productFromDb = productRepository.findById(id).orElse(null);
        if (productFromDb != null) {
          return productRepository.save(product);
        }
      } catch (Exception e) {
        throw new ServiceUnavailable(e);
      }
      // if we made it down to this pint, we did not find the Proudct
      throw new ResourceNotFound("Could not locate a Product with the id: " + id);
    }


}
