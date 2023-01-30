package edu.midlands.training.services;

import edu.midlands.training.entities.Users;
import edu.midlands.training.exceptions.BadDataResponse;
import edu.midlands.training.exceptions.ConflictData;
import edu.midlands.training.exceptions.ResourceNotFound;
import edu.midlands.training.exceptions.ServiceUnavailable;
import edu.midlands.training.repositories.UsersRepository;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

  private final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

  @Autowired
  private UsersRepository usersRepository;

  @Override
  public List<Users> queryUsers(Users users) {
    try {
      if (users.isEmpty()) {
        return usersRepository.findAll();
      } else {
        Example<Users> usersExample = Example.of(users);
        return usersRepository.findAll(usersExample);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  @Override
  public Users getUser(Long id) {
    try {
      Users users = usersRepository.findById(id).orElse(null);

      if (users != null) {
        return users;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
    // if we made it down to this pint, we did not find the Pet
    throw new ResourceNotFound("Could not locate a User with the id: " + id);
  }

  @Override
  public Users addUser(Users user) {

    for (Users u: usersRepository.findAll()){
      if (Objects.equals(u.getEmail().toLowerCase(), user.getEmail().toLowerCase())){
        throw new ConflictData("This email is already in use!");
      }
    }
    try {
      return usersRepository.save(user);
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  @Override
  public Users updateUserById(Users user,Long id) {
    // first, check to make sure the id passed matches the id in the Pet passed
    if (!user.getId().equals(id)) {
      throw new BadDataResponse("User ID must match the ID specified in the URL");
    }

    for (Users u: usersRepository.findAll()){
      if (Objects.equals(u.getEmail().toLowerCase(), user.getEmail().toLowerCase())){
        throw new ConflictData("This email is already in use!");
      }
    }

    try {
      Users userFromDb = usersRepository.findById(id).orElse(null);
      if (userFromDb != null) {
        return usersRepository.save(user);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this pint, we did not find the Pet
    throw new ResourceNotFound("Could not locate a Pet with the id: " + id);
  }

  @Override
  public void deleteUser(Long id) {
    try {
      if (usersRepository.existsById(id)) {
        usersRepository.deleteById(id);
        return;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this pint, we did not find the Pet
    throw new ResourceNotFound("Could not locate a Pet with the id: " + id);
  }
}
