package edu.uga.cs4370.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "user", schema = "moviereview")
public class User {

  @Id
  @NotNull
  private int userID;

  @NotNull
  @Column(length = 50)
  private String username;

  @NotNull
  private String password;

  @NotNull
  private int dob;
}
