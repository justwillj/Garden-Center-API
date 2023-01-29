package edu.midlands.training.data;

import edu.midlands.training.entities.Users;
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

  private Users users1;
  private Users users2;
  private Users users3;


  @Override
  public void run(String... args) throws Exception {
    loadUsers();

  }

  private void loadUsers(){
    users1 = usersRepository.save(new Users("Justin","Dev","Dev 1","email@gmail.com","thisismypasseord"));
  }
}
