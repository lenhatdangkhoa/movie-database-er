package edu.uga.cs4370;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@RestController
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        /* 
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:33306/movie?" +
                    "user=root&password=Lekhoa699");
            Statement stmt = null;
            ResultSet rs = null;

            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("select * from movies;");

                int colCount = rs.getMetaData().getColumnCount();

                while (rs.next()) {
                    for (int i = 1; i <= colCount; i++)
                        System.out.print(rs.getString(i) + "\t");
                    System.out.println();
                }
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            } finally {
                // it is a good idea to release
                // resources in a finally{} block
                // in reverse-order of their creation
                // if they are no-longer needed

                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException sqlEx) {
                    } // ignore

                    rs = null;
                }

                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException sqlEx) {
                    } // ignore

                    stmt = null;
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        */
    }

    @RequestMapping("/")
    public String hello() {
        return "Hello World 2";
    }
}
