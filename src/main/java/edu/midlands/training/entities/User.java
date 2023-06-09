package edu.midlands.training.entities;

import static edu.midlands.training.constants.StringConstants.REQUIRED_FIELD;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.midlands.training.exceptions.ConflictData;
import java.util.Arrays;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This contains all the proprieties and basic validation for our User entities
 */
@Entity
@Table(name = "\"User\"")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "name" + REQUIRED_FIELD)
  @Pattern(regexp = "^[A-Za-z]*$", message = "Invalid Input")
  private String name;

  @NotBlank(message = "title" + REQUIRED_FIELD)
  private String title;

  @NotEmpty(message = "roles" + REQUIRED_FIELD)
  private String[] roles;

  @NotBlank(message = "email" + REQUIRED_FIELD)
  @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Flag.CASE_INSENSITIVE)
  private String email;

  @Length(min = 8)
  @NotBlank(message = "password" + REQUIRED_FIELD)
  private String password;


  public User() {
  }

  public User(String name, String title, String[] roles, String email, String password) {
    this.name = name.trim();
    this.title = title.trim();
    this.roles = roles;
    this.email = email.trim();
    this.password = password.trim();
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
    this.name = name.trim();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title.trim();
  }

  public String[] getRoles() {
    return roles;
  }

  public void setRoles(String[] roles) {
    this.roles = roles;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email.trim();
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password.trim();
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", title='" + title + '\'' +
        ", roles='" + Arrays.toString(roles) + '\'' +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        '}';
  }

  @JsonIgnore
  public boolean isEmpty() {
    return Objects.isNull(id) &&
        Objects.isNull(name) &&
        Objects.isNull(title) &&
        Objects.isNull(roles) &&
        Objects.isNull(email) &&
        Objects.isNull(password);
  }
}
