package edu.midlands.training.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.when;

import edu.midlands.training.entities.User;
import edu.midlands.training.exceptions.ServiceUnavailable;
import edu.midlands.training.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;

class UserServiceImplTest {

  @Mock
  UserRepository userRepository;

  @InjectMocks
  UserServiceImpl userServiceImpl;

  User testUser;
  List<User> testList = new ArrayList<>();

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    testUser = new User("Justin","Dev",new String[]{"EMPLOYEE"},"email@gmail.com","thisismypasseord");
    testUser.setId(1L);

    testList.add(testUser);

    when(userRepository.findAll()).thenReturn(testList);
    when(userRepository.findAll(any(Example.class))).thenReturn(testList);
    when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(testList.get(0)));
    when(userRepository.save(any(User.class))).thenReturn(testList.get(0));
    when(userRepository.saveAll(anyCollection())).thenReturn(testList);

  }

  @Test
  void queryAllUsers() {
    List<User> results = userServiceImpl.queryUsers(new User());
    assertEquals(testList,results);
  }

  @Test
  void queryAllPetsWithSample() {
    List<User> results = userServiceImpl.queryUsers(testUser);
    assertEquals(testList,results);
  }

  @Test
  void queryAllPetsDBError() {
    when(userRepository.findAll()).thenThrow(EmptyResultDataAccessException.class);

    assertThrows(ServiceUnavailable.class,
        () -> userServiceImpl.queryUsers(new User()));

  }
}