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
@Table(name = "Genre", schema = "moviereview")
public class Genre {

  @Id
  @NotNull
  private int genreID;

  @NotNull
  private String username;
}
