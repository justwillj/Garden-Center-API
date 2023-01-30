package edu.midlands.training.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Email.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;
import org.hibernate.validator.constraints.Length;

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
  @Pattern(regexp = "EMPLOYEE|ADMIN")
  private String roles;

  @NotBlank(message = "type is a mandatory field")
  @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Flag.CASE_INSENSITIVE)
  private String email;

  @Length(min = 8)
  @NotBlank(message = "type is a mandatory field")
  private String password;

  public enum AvailableRoles{
    EMPLOYEE,
    ADMIN;
  }

  public Users() {
  }

  public Users(String name, String title, String roles, String email, String password) {
    this.name = name.trim();
    this.title = title.trim();
    this.roles = roles.trim();
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
    return name.trim();
  }

  public void setName(String name) {
    this.name = name.trim();
  }

  public String getTitle() {
    return title.trim();
  }

  public void setTitle(String title) {
    this.title = title.trim();
  }

  public String getRoles() {
    return roles.trim();
  }

  public void setRoles(String roles) {
    this.roles = roles.trim();
  }

  public String getEmail() {
    return email.trim();
  }

  public void setEmail(String email) {
    this.email = email.trim();
  }

  public String getPassword() {
    return password.trim();
  }

  public void setPassword(String password) {
    this.password = password.trim();
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
  public  boolean isEmpty() {
    return Objects.isNull(id) &&
        Objects.isNull(name) &&
        Objects.isNull(title) &&
        Objects.isNull(roles) &&
        Objects.isNull(email) &&
        Objects.isNull(password);
  }
}
