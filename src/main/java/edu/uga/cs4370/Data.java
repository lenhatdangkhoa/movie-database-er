package edu.uga.cs4370;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Data {
    Connection conn = null;

    public Data() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:33306/movie_rating?" +
            "user=root&password=Lekhoa699");
            System.out.println("Connection successful");
        } catch (SQLException sqle) {
             // handle any errors
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("SQLState: " + sqle.getSQLState());
            System.out.println("VendorError: " + sqle.getErrorCode());
        }
    }
    /**
     * ONLY USE THIS METHOD ONCE TO INITIALIZE THE DATABASE.
     */
    public void initializeData() {
        ResultSet rs = null;
        Statement st = null;
        try {
            st = conn.createStatement();
            // String query = "CREATE TABLE User(" +
            //     "UserID INT UNIQUE NOT NULL, " +
            //     "username VARCHAR(20), " + 
            //     "password VARCHAR(12), " +
            //     "dob DATE, " + 
            //     "PRIMARY KEY(UserID))";
            // st.execute(query);
            String query = "CREATE TABLE Comment(" +
            "CommentID INT UNIQUE NOT NULL, " +
            "timestamp TIME, " +
            "comment VARCHAR(150), " +
            "rating FLOAT, " +
            "PRIMARY KEY(CommentID))"
            ;
            st.execute(query);
            System.out.println(rs);
        } catch (SQLException sqle) {
             // handle any errors
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("SQLState: " + sqle.getSQLState());
            System.out.println("VendorError: " + sqle.getErrorCode());
        }
    }
    public static void main(String[] args) {
        Data data = new Data();
        data.initializeData();

    }
}
