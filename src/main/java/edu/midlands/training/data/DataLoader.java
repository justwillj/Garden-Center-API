package edu.midlands.training.data;

import edu.midlands.training.entities.Address;
import edu.midlands.training.entities.Customers;
import edu.midlands.training.entities.Users;
import edu.midlands.training.repositories.AddressRepository;
import edu.midlands.training.repositories.CustomersRepository;
import edu.midlands.training.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

  private final Logger logger = LoggerFactory.getLogger(DataLoader.class);
  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private CustomersRepository customersRepository;

  @Autowired
  private AddressRepository addressRepository;



  private Users users1;
  private Users users2;
  private Users users3;

  private Customers customer1;
  private Customers customer2;
  private Customers customer3;


  private Address address1;
  private Address address2;



  @Override
  public void run(String... args) throws Exception {
    loadUsers();
    loadAddress();
    loadCustomers();

  }

  private void loadUsers(){
    users1 = usersRepository.save(new Users("Justin","Dev","EMPLOYEE","email@gmail.com","thisismypasseord"));
    users2 = usersRepository.save(new Users("Austin","Bank","ADMIN","test@gmail.com","testtest"));
  }

  private void loadAddress(){
    address1 = addressRepository.save(new Address("1169 Boone Crockett Lane","Olympia","WA","98501"));
    address2 = addressRepository.save(new Address("4021 Cedar Street","Batesville","AR","72501-1234"));

  }
  private void loadCustomers(){
    customer1 = customersRepository.save(new Customers("John","john@gmail.com",address1));
    customer2 = customersRepository.save(new Customers("David","david@gmail.com",address2));
    customer3 = customersRepository.save(new Customers("Alex","alex@gmail.com",address1));

  }
}
