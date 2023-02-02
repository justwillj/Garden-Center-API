package edu.midlands.training.entities;

import static edu.midlands.training.constants.StringConstants.REQUIRED_FIELD;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "title"+ REQUIRED_FIELD)
  private String sku;

  @NotBlank(message = "title"+ REQUIRED_FIELD)
  private String type;

  @NotBlank(message = "title"+ REQUIRED_FIELD)  private String name;
  private String description;

  @NotBlank(message = "title"+ REQUIRED_FIELD)
  private String manufacturer;

  @NotNull(message = "title"+ REQUIRED_FIELD)
  @DecimalMin(value = "0.00")
  private BigDecimal price;

  public Product() {
  }

  public Product(String sku, String type, String name, String description, String manufacturer,
      BigDecimal price) {
    this.sku = sku;
    this.type = type;
    this.name = name;
    this.description = description;
    this.manufacturer = manufacturer;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        ", sku='" + sku + '\'' +
        ", type='" + type + '\'' +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", manufacturer='" + manufacturer + '\'' +
        ", price=" + price +
        '}';
  }

  @JsonIgnore
  public  boolean isEmpty() {
    return Objects.isNull(id) &&
        Objects.isNull(sku) &&
        Objects.isNull(type) &&
        Objects.isNull(name) &&
        Objects.isNull(description) &&
        Objects.isNull(manufacturer);
  }
}
