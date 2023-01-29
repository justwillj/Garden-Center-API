package edu.midlands.training.entities;

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

}
