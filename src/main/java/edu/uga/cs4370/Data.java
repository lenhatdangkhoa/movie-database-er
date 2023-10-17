package edu.uga.cs4370;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.FileReader;  
import java.io.IOException; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Data {
    public Connection conn = null;
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
    public void initializeTables() {
        Statement st = null;
        try {
            st = conn.createStatement();
            String query = "CREATE TABLE User(" +
                "UserID INT UNIQUE NOT NULL AUTO_INCREMENT, " +
                "username VARCHAR(25), " + 
                "password VARCHAR(25), " +
                "dob VARCHAR(10), " + 
                "PRIMARY KEY(UserID))";
            st.execute(query);
            query = "CREATE TABLE Comment(" +
            "CommentID INT UNIQUE NOT NULL AUTO_INCREMENT, " +
            "timestamp TEXT, " +
            "comment VARCHAR(150), " +
            "rating FLOAT, " +
            "UserID INT NOT NULL, " +
            "PRIMARY KEY(CommentID), " +
            "FOREIGN KEY (UserID) REFERENCES User(UserID));";
            st.execute(query);
            query = "ALTER TABLE User ADD CommentID INT;";
            st.execute(query);
            query = "ALTER TABLE User ADD FOREIGN KEY(CommentID) REFERENCES Comment(CommentID);";
            st.execute(query);
            query = "CREATE TABLE Movie(" +
            "MovieID INT UNIQUE NOT NULL AUTO_INCREMENT, " +
            "rating FLOAT, " +
            "movie_name VARCHAR(100), " +
            "description VARCHAR(200), " +
            "PRIMARY KEY(MovieID));";
            st.execute(query);
            query = "CREATE TABLE Genre(" +
            "GenreID INT UNIQUE NOT NULL AUTO_INCREMENT, " +
            "genre_name VARCHAR(50), " +
            "PRIMARY KEY(GenreID));";
            st.execute(query);
            query = "CREATE TABLE MovieComment(" +
            "MovieCommentID INT NOT NULL AUTO_INCREMENT, " +
            "MovieID INT NOT NULL, " +
            "CommentID INT NOT NULL, " +
            "PRIMARY KEY(MovieCommentID), " +
            "FOREIGN KEY(MovieID) REFERENCES Movie(MovieID), " +
            "FOREIGN KEY(CommentID) REFERENCES Comment(CommentID));";
            st.execute(query);
            query = "CREATE TABLE MovieGenre(" +
            "MovieGenreID INT NOT NULL, " +
            "MovieID INT NOT NULL, " +
            "GenreID INT NOT NULL, " +
            "PRIMARY KEY(MovieGenreID), " +
            "FOREIGN KEY(MovieID) REFERENCES Movie(MovieID), " +
            "FOREIGN KEY(GenreID) REFERENCES Genre(GenreID));";
            st.execute(query);


        } catch (SQLException sqle) {
             // handle any errors
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("SQLState: " + sqle.getSQLState());
            System.out.println("VendorError: " + sqle.getErrorCode());
        }
    }

    public void initializeData() {
        Statement st = null;
        try {
            CSVReader csvReader = new CSVReaderBuilder(new FileReader("./src/dataset/movies.csv")).withSkipLines(1).build();

            List<String[]> data = csvReader.readAll();
            Set<String> genres = new HashSet<>();
            for (int i = 0; i < 50; i++) {
                try {
                    String query = "INSERT INTO Movie VALUES("+ (i+1) + "," + (1 + new Random().nextFloat() * 4) + ",\"" +
                    data.get(i)[1] +"\", \"Very cool movie! Should watch!\");" ;
                    st = conn.createStatement();
                    st.execute(query);
                } catch(SQLException sqle) {
                    // handle any errors
                    System.out.println("SQLException: " + sqle.getMessage());
                    System.out.println("SQLState: " + sqle.getSQLState());
                    System.out.println("VendorError: " + sqle.getErrorCode());
                }
                for (String genre : data.get(i)[2].split("\\|")) {
                    genres.add(genre);
                }

            }
            for (String genre : genres) {
                String query = "INSERT INTO Genre(genre_name) VALUES(\"" + genre +"\");";
                try {
                    st = conn.createStatement();
                    st.execute(query);
                } catch(SQLException sqle) {
                    //handle any errors
                    System.out.println("SQLException: " + sqle.getMessage());
                    System.out.println("SQLState: " + sqle.getSQLState());
                    System.out.println("VendorError: " + sqle.getErrorCode());
                }
            }
            List<String> list = new ArrayList<>(genres);
            int j = 0;
            for (int i = 0; i < 50; i++) {
                for (String genre : data.get(i)[2].split("\\|")) {
                    String query = "INSERT INTO MovieGenre VALUES("+ (j + 1) + "," +
                    (i + 1) + "," + (list.indexOf(genre) + 1) +");";
                    try {
                    st = conn.createStatement();
                    st.execute(query);
                    j++;
                } catch(SQLException sqle) {
                    // handle any errors
                    System.out.println("SQLException: " + sqle.getMessage());
                    System.out.println("SQLState: " + sqle.getSQLState());
                    System.out.println("VendorError: " + sqle.getErrorCode());
                }
                }
            }
            
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
    public static void main(String[] args) {
        Data data = new Data();
        data.initializeTables();
        data.initializeData();

    }
}
