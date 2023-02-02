package edu.midlands.training.data;

import edu.midlands.training.entities.Address;
import edu.midlands.training.entities.Customer;
import edu.midlands.training.entities.Product;
import edu.midlands.training.entities.User;
import edu.midlands.training.repositories.AddressRepository;
import edu.midlands.training.repositories.CustomerRepository;
import edu.midlands.training.repositories.ProductRepository;
import edu.midlands.training.repositories.UserRepository;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

  private final Logger logger = LoggerFactory.getLogger(DataLoader.class);
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private ProductRepository productRepository;


  private User user1;
  private User user2;
  private User user3;

  private Customer customer1;
  private Customer customer2;
  private Customer customer3;


  private Address address1;
  private Address address2;

  private Product product1;
  private Product product2;
  private Product product3;



  @Override
  public void run(String... args) throws Exception {
    loadUsers();
    loadAddresses();
    loadCustomers();
    loadProducts();
  }

  private void loadUsers(){
    user1 = userRepository.save(new User("Justin","Dev",new String[]{"EMPLOYEE"},"email@gmail.com","thisismypasseord"));
    user2 = userRepository.save(new User("Austin","Bank",new String[]{"ADMIN"},"test@gmail.com","testtest"));
  }

  private void loadAddresses(){
    address1 = addressRepository.save(new Address("1169 Boone Crockett Lane","Olympia","WA","98501"));
    address2 = addressRepository.save(new Address("4021 Cedar Street","Batesville","AR","72501-1234"));
  }
  private void loadCustomers(){
    customer1 = customerRepository.save(new Customer("John","john@gmail.com",address1));
    customer2 = customerRepository.save(new Customer("David","david@gmail.com",address2));
    customer3 = customerRepository.save(new Customer("Alex","alex@gmail.com",address1));
  }

  private void loadProducts(){
    product1 = productRepository.save(new Product("TS12356","Shoes","Leather Platform","Really cool shoes!","Dr. Martens",new BigDecimal(
        "26.10")));
    product2 = productRepository.save(new Product("KS93528TUT","Pants","Blue Jeans","The best jeans ever!","Levi's",new BigDecimal(
        "56.99")));
    product3 = productRepository.save(new Product("WR-524927","Shirt","Long Sleeve","The worlds greatest shirt","Adidas",new BigDecimal(
        "29.56")));

  }
}
