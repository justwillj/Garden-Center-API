package edu.midlands.training.entities;

import static edu.midlands.training.constants.StringConstants.REQUIRED_FIELD;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.aspectj.weaver.ast.Or;

@Entity
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "productId" + REQUIRED_FIELD)
  private Long productId;
  @NotNull(message = "quantity" + REQUIRED_FIELD)
  @DecimalMin(value = "0.00", message = "quantity must be a positive number!")
  private Integer quantity;

  @OneToMany(mappedBy = "items", cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<Order> orders;

  public Item() {
  }

  public Item(Long productId, Integer quantity) {
    this.productId = productId;
    this.quantity = quantity;

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Set<Order> getOrders() {
    return orders;
  }

  public void setOrders(Set<Order> orders) {
    this.orders = orders;
  }

  @Override
  public String toString() {
    return "Item{" +
        "id=" + id +
        ", productId=" + productId +
        ", quantity=" + quantity +
        ", orders=" + orders +
        '}';
  }

  @JsonIgnore
  public boolean isEmpty() {
    return Objects.isNull(id) &&
        Objects.isNull(productId) &&
        Objects.isNull(quantity) &&
        Objects.isNull(orders);
  }
}
