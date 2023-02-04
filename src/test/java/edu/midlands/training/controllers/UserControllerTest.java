package edu.midlands.training.controllers;

import static edu.midlands.training.constants.StringConstants.CONTEXT_USERS;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.midlands.training.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
@DirtiesContext
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  ResultMatcher okStatus = MockMvcResultMatchers.status().isOk();
  ResultMatcher createdStatus = MockMvcResultMatchers.status().isCreated();
  ResultMatcher deletedStatus = MockMvcResultMatchers.status().isNoContent();

  ResultMatcher expectedType = MockMvcResultMatchers.content()
      .contentType(MediaType.APPLICATION_JSON);

  ObjectMapper mapper = new ObjectMapper();

  @Autowired
  private WebApplicationContext wac;
  private MockMvc mockMvc;


  @BeforeEach
  public void setUp() {
    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
    this.mockMvc = builder.build();

  }

  @Test

  void getUsersReturnsThree() throws Exception {
    mockMvc
        .perform(get(CONTEXT_USERS))
        .andExpect(okStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  void getUserThatDoesExistById() throws Exception {
    mockMvc
        .perform(get(CONTEXT_USERS + "/1"))
        .andExpect(okStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$.name", is("Justin")));
  }

  @Test
  @DirtiesContext
  void postNewUser() throws Exception {
    User user1 = new User("Josh","Dev 2",new String[]{"EMPLOYEE"},"Josh2@gmail.com","joshpassword");
    String userAsString = mapper.writeValueAsString(user1);

    this.mockMvc
        .perform(post(CONTEXT_USERS)
            .contentType(MediaType.APPLICATION_JSON)
            .content(userAsString))
        .andExpect(createdStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$.title", is("Dev 2")));
  }

  @Test
  void putUser() throws Exception {
    User user1 = new User("Justin","Dev",new String[]{"EMPLOYEE"},"email@gmail.com","thisismypasseord");
    user1.setId(1L);
    String userAsString = mapper.writeValueAsString(user1);
    this.mockMvc
        .perform(put(CONTEXT_USERS + "/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userAsString))
        .andExpect(okStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$.email", is("email@gmail.com")));

  }


  @Test
  void deleteUser() throws Exception {
    mockMvc
        .perform(delete(CONTEXT_USERS + "/2"))
        .andExpect(deletedStatus);
  }

}