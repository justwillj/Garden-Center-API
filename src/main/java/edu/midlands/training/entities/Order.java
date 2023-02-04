package edu.midlands.training.entities;

import static edu.midlands.training.constants.StringConstants.REQUIRED_FIELD;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "\"order\"")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "customerId"+ REQUIRED_FIELD)
  private Long customerId;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,pattern = "yyyy-MM-dd")
  @NotNull(message = "date"+ REQUIRED_FIELD)
  private LocalDate date;
  @NotNull(message = "orderTotal"+ REQUIRED_FIELD)
  @DecimalMin(value = "0.00",message = "total must be a positive number!")
  private BigDecimal orderTotal;

  @ManyToOne(fetch = FetchType.EAGER)
  @NotNull
  @JoinColumn
  private Item items;

  public Order() {
  }

  public Order(Long customerId, LocalDate date, BigDecimal orderTotal, Item items) {
    this.customerId = customerId;
    this.date = date;
    this.orderTotal = orderTotal;
    this.items = items;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public BigDecimal getOrderTotal() {
    return orderTotal;
  }

  public void setOrderTotal(BigDecimal orderTotal) {
    this.orderTotal = orderTotal;
  }

  public Item getItems() {
    return items;
  }

  public void setItems(Item items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "Order{" +
        "id=" + id +
        ", customerId=" + customerId +
        ", date=" + date +
        ", orderTotal=" + orderTotal +
        ", items=" + items +
        '}';
  }

  @JsonIgnore
  public  boolean isEmpty() {
    return Objects.isNull(id) &&
        Objects.isNull(customerId) &&
        Objects.isNull(date) &&
        Objects.isNull(orderTotal) &&
        Objects.isNull(items);
  }
}
