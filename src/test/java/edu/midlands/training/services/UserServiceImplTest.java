package edu.midlands.training.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import edu.midlands.training.entities.User;
import edu.midlands.training.exceptions.BadDataResponse;
import edu.midlands.training.exceptions.ConflictData;
import edu.midlands.training.exceptions.ResourceNotFound;
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
  User testUser2;
  User testUser3;
  List<User> testList = new ArrayList<>();

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    testUser = new User("Justin","Dev",new String[]{"EMPLOYEE"},"email@gmail.com","thisismypasseord");
    testUser2 = new User("Josh","Dev",new String[]{"EMPLOYEE"},"josh@gmail.com","thisismypasseord");
    testUser3 = new User("test","Dev",new String[]{"wrongRole"},"josh@gmail.com","thisismypasseord");
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
  void queryAllUsersWithSample() {
    List<User> results = userServiceImpl.queryUsers(testUser);
    assertEquals(testList,results);
  }

  @Test
  void queryAllUsersDBError() {
    when(userRepository.findAll()).thenThrow(EmptyResultDataAccessException.class);

    assertThrows(ServiceUnavailable.class,
        () -> userServiceImpl.queryUsers(new User()));
  }

  @Test
  public void getUser() {
    User result = userServiceImpl.getUser(1L);
    assertEquals(testUser, result);
  }

  @Test
  public void getUserDBError() {
    when(userRepository.findById(anyLong())).thenThrow(EmptyResultDataAccessException.class);
    assertThrows(ServiceUnavailable.class,
        () -> userServiceImpl.getUser(1L));
  }

  @Test
  public void getUserNotFound() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
    Exception exception = assertThrows(ResourceNotFound.class,
        () -> userServiceImpl.getUser(1L));
    String expectedMessage = "Could not locate a User with the id: 1";
    assertEquals(expectedMessage,
        exception.getMessage(),
        () -> "Message did not equal '" + expectedMessage + "', actual message:"
            + exception.getMessage());
  }

  @Test
  public void addUser() {
    User result = userServiceImpl.addUser(testUser2);
    assertEquals(testUser, result);
  }

  @Test
  public void addUserDBError() {
    when(userRepository.save(any(User.class))).thenThrow(
        new EmptyResultDataAccessException("Database unavailable", 0));
    assertThrows(ServiceUnavailable.class,
        () -> userServiceImpl.addUser(testUser2));
  }

  @Test
  public void addUserEmailAlreadyUsed() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
    Exception exception = assertThrows(BadDataResponse.class,
        () -> userServiceImpl.addUser(testUser));
    String expectedMessage = "This email is already in use!";
    assertEquals(expectedMessage,
        exception.getMessage(),
        () -> "Message did not equal '" + expectedMessage + "', actual message:"
            + exception.getMessage());
  }

  @Test
  public void addUserInvalidRoles() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
    Exception exception = assertThrows(ConflictData.class,
        () -> userServiceImpl.addUser(testUser3));
    String expectedMessage = "Please use a valid role!";
    assertEquals(expectedMessage,
        exception.getMessage(),
        () -> "Message did not equal '" + expectedMessage + "', actual message:"
            + exception.getMessage());
  }
}