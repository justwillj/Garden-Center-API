package edu.midlands.training.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "\"order\"")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long customerId;

//  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//  @JsonFormat(pattern = "yyyy/MM/dd")
  private LocalDate date;

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
}
