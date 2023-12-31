package edu.uga.cs4370.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("dynamic")
public class WebController {
    Connection conn = null;
    /**
     * Connecting to the database when the constructor is called.
     */
    public WebController() {
         try {
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:33306/movie_rating?" +
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
     * A get request endpoint at /dynamic/movies to retrieve all movies from the database.
     * @return html
     */
    @GetMapping("/movies")
    public ModelAndView getMovies() {
        ModelAndView mv = new ModelAndView("movies");
        List<String> movies = new ArrayList<>();
        List<String> ratings = new ArrayList<>();
        List<String> genres = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet res =st.executeQuery("SELECT * FROM Movie");
            while (res.next()) {
                String temp = "";
                switch (Math.round(res.getFloat("rating"))) {
                    case 1:
                        temp = "⭐️";
                        break;
                    case 2:
                        temp = "⭐️⭐️";
                        break;
                    case 3:
                        temp = "⭐️⭐️⭐️";
                        break;
                    case 4:
                        temp = "⭐️⭐️⭐️⭐️";
                        break;
                    case 5:
                        temp = "⭐️⭐️⭐️⭐️⭐️";
                        break;  
                    default:
                        break;
                }
                movies.add(res.getString("movie_name") );
                ratings.add(temp);

            }
            
            mv.addObject("ratings", ratings);
            for (String movie : movies) {
                ResultSet res2 = st.executeQuery("SELECT genre_name FROM MovieGenre " +
                    "JOIN Genre ON MovieGenre.GenreID = Genre.GenreID " +
                    "JOIN Movie ON MovieGenre.MovieID = Movie.MovieID " + 
                    "WHERE movie_name = \"" + movie + "\"");
                String genre = "";
                while (res2.next()) {
                    genre += res2.getString("genre_name") + "  ";
                }
                
                genres.add(genre);
            }
            for (int i = 0; i < movies.size(); i++) {
                String temp = "";
                if (genres.get(i).length() == 0) {
                    temp = movies.get(i) + "| Action";
                } else {
                    temp = movies.get(i) + "| " + genres.get(i);
                }
                movies.set(i, temp);
            }
            mv.addObject("movies", movies);

        } catch (SQLException sqle) {
             // handle any errors
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("SQLState: " + sqle.getSQLState());
            System.out.println("VendorError: " + sqle.getErrorCode());
        }
        
        return mv;
    } 
    @GetMapping("registration")
    public ModelAndView registration() {
        ModelAndView mv = new ModelAndView("registration");
        mv.addObject("error", "");
        return mv;
    }

    /**
     * Handle POST request for creating user's account. 
     * @param username
     * @param password
     * @param dob
     * @return
     */
    @PostMapping("createaccount")
    public String createAccount(@RequestParam String username, String password, String dob) {
        try {
            Statement st = conn.createStatement();
            String query = "INSERT INTO User (username, password, dob, CommentID) VALUES (\"" +
            username + "\",\"" + password + "\",\"" + dob + "\",NULL);";
            st.execute(query);
        } catch (SQLException sqle) {
            // handle any errors
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("SQLState: " + sqle.getSQLState());
            System.out.println("VendorError: " + sqle.getErrorCode());
        }
        return "redirect:/dynamic/movies";
    }

    @PostMapping("deletemovie")
    public String createAccount(@RequestParam String movie_name) {
        try {
            Statement st = conn.createStatement();
            String query = "SELECT * FROM Movie WHERE movie_name = \"" + movie_name + "\"";
            ResultSet res = st.executeQuery(query);
            String movieid = "";
            while (res.next()) {
                movieid = res.getString("MovieID");
            }
            st.execute("DELETE FROM MovieComment WHERE MovieID = " + movieid);
            st.execute("DELETE FROM MovieGenre WHERE MovieID = " + movieid);
            st.execute("DELETE FROM Movie WHERE MovieID = " + movieid);

        } catch (SQLException sqle) {
            // handle any errors
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("SQLState: " + sqle.getSQLState());
            System.out.println("VendorError: " + sqle.getErrorCode());
        }
        return "redirect:/dynamic/movies";
    }

    @PostMapping("getaccount")
    public String checkAccount(@RequestParam String username, String password) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT UserID FROM User WHERE username=\"" +
            username +"\"AND password=\"" + password +"\";");
            
            if (rs.next()) { // checks if the set is empty
                return "redirect:/dynamic/getaccount?id=" + rs.getInt("UserID");           
            } 

        } catch (SQLException sqle) {
            // handle any errors
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("SQLState: " + sqle.getSQLState());
            System.out.println("VendorError: " + sqle.getErrorCode());
        }
        return "redirect:/dynamic/registration";
    }

    /**
     * 
     * @param id
     * @return
     */
    @GetMapping("getaccount")
    public ModelAndView getAccount(@RequestParam("id") int id) {
        ModelAndView mv = new ModelAndView("user");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM User Where UserID = "+id);
            rs.next();
            mv.addObject("username", rs.getString("username"));
            mv.addObject("id", id);
            List<String> movies = new ArrayList<>();
            List<String> moviesId = new ArrayList<>();
            rs = st.executeQuery("SELECT * FROM Movie");
            while (rs.next()) {
                movies.add(rs.getString("movie_name"));
                moviesId.add(rs.getString("MovieID"));
            }
            mv.addObject("movies", movies);
            mv.addObject("movieid", moviesId);
        } catch (SQLException sqle) {
             // handle any errors
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("SQLState: " + sqle.getSQLState());
            System.out.println("VendorError: " + sqle.getErrorCode());
        }
        return mv;
    }

   
    @PostMapping("writecomment")
    public String writeComment(@RequestParam String comment, String userid, String movieid ) {
        try {
            Statement st = conn.createStatement();
            String query = "INSERT INTO Comment(timestamp, comment, rating, UserID) VALUES (\"" +
            java.time.LocalDate.now().toString() + "\",\"" + comment + "\"," +
            (new Random().nextInt(5) + 1) +"," + userid + ")";
            st.execute(query); 
            ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID()");
            int commentID = 0;
            if (rs.next()) {
                commentID = rs.getInt(1);
            }
            query = "INSERT INTO MovieComment(MovieID, CommentID) VALUES (" + movieid + "," + commentID + ")" ;
            st.execute(query);
        } catch (SQLException sqle) {
             // handle any errors
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("SQLState: " + sqle.getSQLState());
            System.out.println("VendorError: " + sqle.getErrorCode());
        }
        return "redirect:/dynamic/getaccount?id=" + userid;
    }

    @GetMapping("/viewyourcomment")
    public ModelAndView viewComment(@RequestParam("userid") int userid) {
        ModelAndView mv = new ModelAndView("comments");
        List<String> comments = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            String query = "SELECT comment,movie_name FROM MovieComment "+
            "JOIN Comment ON MovieComment.CommentID = Comment.CommentID " +
            "AND Comment.UserID =" + userid + 
            " JOIN Movie ON MovieComment.MovieID = Movie.MovieID;";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String temp = rs.getString("movie_name") + ": " + "\"" + 
                rs.getString("comment") +"\"";
                comments.add(temp);
            }
            mv.addObject("comments", comments);
            mv.addObject("userid", userid);
        } catch (SQLException sqle) {
             // handle any errors
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("SQLState: " + sqle.getSQLState());
            System.out.println("VendorError: " + sqle.getErrorCode());
        }
        return mv;
    }

    @GetMapping("allcomments")
    public ModelAndView showAllComments() {
        ModelAndView mv = new ModelAndView("allcomments");
        List<String> descriptions = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            String query = "SELECT username, movie_name, comment, timestamp FROM MovieComment " +
            "JOIN Comment ON MovieComment.CommentID = Comment.CommentID " +
            "JOIN Movie ON MovieComment.MovieID = Movie.MovieID " +
            "JOIN User ON Comment.UserID = User.UserID;";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String temp = rs.getString("movie_name") + ": \"" + 
                rs.getString("comment") + ".\" By : " + rs.getString("username") +
                " (" + rs.getString("timestamp") + ")";
                descriptions.add(temp);
            }
            mv.addObject("descriptions", descriptions);
            rs = st.executeQuery("SELECT COUNT(*) as total_comments FROM MovieComment");
            rs.next();
            int commentCounts = Integer.parseInt(rs.getString("total_comments"));
            mv.addObject("commentcounts", commentCounts);

        } catch (SQLException sqle) {
            // handle any errors
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("SQLState: " + sqle.getSQLState());
            System.out.println("VendorError: " + sqle.getErrorCode());
        }
        return mv;
    }


}
