package edu.uga.cs4370.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @GetMapping("/main-page")
    public ModelAndView page() {
        ModelAndView mv = new ModelAndView("main");
        mv.addObject("message", new int[]{1,2,3,4,5});
        mv.addObject("test", "easy");
        return mv;
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
                movies.add(res.getString("movie_name"));
                ratings.add(temp);
            }
            mv.addObject("movies", movies);
            mv.addObject("ratings", ratings);
        } catch (SQLException sqle) {
             // handle any errors
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("SQLState: " + sqle.getSQLState());
            System.out.println("VendorError: " + sqle.getErrorCode());
        }
        
        return mv;
    } 
    
}
