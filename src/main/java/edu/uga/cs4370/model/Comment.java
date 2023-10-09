package edu.uga.cs4370.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "comments", schema = "moviereview")
public class Comment {
  private long commentID;
  private int Rating;
  private String Comment;

  public Comment(long commentID, int Rating, String Comment){
    this.commentID = commentID;
    this.Rating = Rating;
    this.Comment = Comment;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public long getCommentID(){
    return commentID;
  }
public void setCommentID
}
