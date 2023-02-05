package edu.midlands.training.controllers;

import static edu.midlands.training.constants.StringConstants.CONTEXT_CUSTOMERS;
import static edu.midlands.training.constants.StringConstants.CONTEXT_PRODUCTS;
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
import edu.midlands.training.entities.Product;
import edu.midlands.training.entities.User;
import java.math.BigDecimal;
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
class ProductControllerTest {


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
  void getProductsReturnsThree() throws Exception {
    mockMvc
        .perform(get(CONTEXT_PRODUCTS))
        .andExpect(okStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  void getProductThatDoesExistById() throws Exception {
    mockMvc
        .perform(get(CONTEXT_PRODUCTS + "/1"))
        .andExpect(okStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$.type", is("Shoes")));
  }

  @Test
  @DirtiesContext
  void postNewProduct() throws Exception {
    Product product1 = new Product("TE-46223","Shoes","Leather Platform","Really cool shoes!","Dr. Martens"
        ,new BigDecimal("26.10"));
    String productAsString = mapper.writeValueAsString(product1);

    this.mockMvc
        .perform(post(CONTEXT_PRODUCTS)
            .contentType(MediaType.APPLICATION_JSON)
            .content(productAsString))
        .andExpect(createdStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$.name", is("Leather Platform")));
  }

  @Test
  void putProduct() throws Exception {
    Product product1 = new Product("TS12356","Shoes","Leather Platform","Really cool shoes!","Dr. Martens",new BigDecimal(
    "26.10"));
    product1.setId(1L);
    String productAsString = mapper.writeValueAsString(product1);
    this.mockMvc
        .perform(put(CONTEXT_PRODUCTS + "/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(productAsString))
        .andExpect(okStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$.sku", is("TS12356")));

  }

  @Test
  void deleteProduct() throws Exception {
    mockMvc
        .perform(delete(CONTEXT_PRODUCTS + "/2"))
        .andExpect(deletedStatus);
  }
}