package edu.midlands.training.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Users {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "type is a mandatory field")
  private String name;

  @NotBlank(message = "type is a mandatory field")
  private String title;

  @NotBlank(message = "type is a mandatory field")
  private String roles;

  @NotBlank(message = "type is a mandatory field")
  private String email;

  @NotBlank(message = "type is a mandatory field")
  private String password;

  public Users() {
  }

  public Users(String name, String title, String roles, String email, String password) {
    this.name = name;
    this.title = title;
    this.roles = roles;
    this.email = email;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getRoles() {
    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "Users{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", title='" + title + '\'' +
        ", roles='" + roles + '\'' +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        '}';
  }

  @JsonIgnore
  public static boolean isEmpty() {
    return Objects.isNull(id) &&
        Objects.isNull(name) &&
        Objects.isNull(title) &&
        Objects.isNull(roles) &&
        Objects.isNull(email) &&
        Objects.isNull(password);
  }
}
