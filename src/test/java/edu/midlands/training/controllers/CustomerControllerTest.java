package edu.midlands.training.controllers;

import static edu.midlands.training.constants.StringConstants.CONTEXT_CUSTOMERS;
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
import edu.midlands.training.entities.Address;
import edu.midlands.training.entities.Customer;
import edu.midlands.training.entities.User;
import edu.midlands.training.repositories.AddressRepository;
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
class CustomerControllerTest {

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
  void getCustomerReturnsThree() throws Exception {
    mockMvc
        .perform(get(CONTEXT_CUSTOMERS))
        .andExpect(okStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  void getCustomerThatDoesExistById() throws Exception {
    mockMvc
        .perform(get(CONTEXT_CUSTOMERS + "/1"))
        .andExpect(okStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$.name", is("John")));
  }

  @Test
  @DirtiesContext
  void postNewCustomer() throws Exception {
    String json = "{\"name\":\"Lee\",\"email\":\"lee@gmail.com\",\"address\":{\"id\":1,\"street\":\"1169 Boone Crockett Lane\",\"city\":\"Olympia\",\"state\":\"WA\",\"zipCode\":\"98501\"}}";
//    Address address1 = new Address("1169 Boone Crockett Lane","Olympia","WA","98501");
//    Customer customer1 = new Customer("Lee","lee@gmail.com",new Address());
//    customer1.setAddress(address1);
//    String customerAsString = mapper.writeValueAsString(customer1);

    this.mockMvc
        .perform(post(CONTEXT_CUSTOMERS)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(createdStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$.name", is("Lee")));
  }

  @Test
  void putCustomer() throws Exception {
    String json = "{\"id\":1,\"name\":\"Lee\",\"email\":\"lee@gmail.com\",\"address\":{\"id\":1,\"street\":\"1169 Boone Crockett Lane\",\"city\":\"Olympia\",\"state\":\"WA\",\"zipCode\":\"98501\"}}";
////    Address address1 = new Address("1169 Boone Crockett Lane","Olympia","WA","98501");
////    Customer customer1 = new Customer("Lee","lee@gmail.com",address1);
//    customer1.setId(1L);
//    String userAsString = mapper.writeValueAsString(customer1);
    this.mockMvc
        .perform(put(CONTEXT_CUSTOMERS + "/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(okStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$.email", is("lee@gmail.com")));
  }

  @Test
  void deleteCustomer() throws Exception {
    mockMvc
        .perform(delete(CONTEXT_CUSTOMERS + "/2"))
        .andExpect(deletedStatus);
  }

}