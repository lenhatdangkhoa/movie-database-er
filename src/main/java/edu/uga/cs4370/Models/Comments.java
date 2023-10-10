package edu.uga.cs4370.Models;

public class Comments {

  private Long comments_id;
  private int rating;
  private String comment;

  // might need to turn into date format
  // https://stackoverflow.com/questions/40150175/most-appropriate-sql-and-java-data-types-for-storing-date-and-time
  private String timestamp;

  // FK reference to Users
  private Long user_id;

  // getter & setter
  public long getUserID() {
    return user_id;
  }

  public void setUserID(Long user_id) {
    this.user_id = user_id;
  }
}
