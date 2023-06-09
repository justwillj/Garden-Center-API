package edu.midlands.training.controllers;

import static edu.midlands.training.constants.StringConstants.CONTEXT_CUSTOMERS;
import static edu.midlands.training.constants.StringConstants.CONTEXT_ORDERS;
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
class OrderControllerTest {
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
  void getOrderReturnsThree() throws Exception {
    mockMvc
        .perform(get(CONTEXT_ORDERS))
        .andExpect(okStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  void getOrderThatDoesExistById() throws Exception {
    mockMvc
        .perform(get(CONTEXT_ORDERS + "/1"))
        .andExpect(okStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$.customerId", is(2)));
  }

  @Test
  @DirtiesContext
  void postNewOrder() throws Exception {
    String json = "{\"customerId\":\"2\",\"date\":\"2019-12-22\",\"orderTotal\":\"23.43\",\"items\":{\"id\":1,\"productId\":\"1 \",\"quantity\":\"3\"}}";
    this.mockMvc
        .perform(post(CONTEXT_ORDERS)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(createdStatus)
        .andExpect(expectedType);
  }

  @Test
  void putOrder() throws Exception {
    String json = "{\"id\":1,\"customerId\":\"2\",\"date\":\"2019-12-22\",\"orderTotal\":\"23.43\",\"items\":{\"id\":1,\"productId\":\"1 \",\"quantity\":\"3\"}}";
    this.mockMvc
        .perform(put(CONTEXT_ORDERS + "/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(okStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$.date", is("2019-12-22")));
  }

  @Test
  void deleteProduct() throws Exception {
    mockMvc
        .perform(delete(CONTEXT_ORDERS + "/2"))
        .andExpect(deletedStatus);
  }

}