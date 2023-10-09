package edu.uga.cs4370.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "comments", schema = "moviereview")
public class Comment {

  private long commentID;
  private int Rating;
  private String Comment;
}
