package edu.uga.cs4370.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("dynamic")
public class WebController {
    

    @GetMapping("/main-page")
    public ModelAndView page() {
        ModelAndView mv = new ModelAndView("page1");
        mv.addObject("message", "hello world!");
        return mv;
    }   

    /**
     * A get request endpoint at /dynamic/movies to retrieve all movies from the database.
     * @return html
     */
    @GetMapping("/movies")
    @ResponseBody
    public String getMovies() {
        return "<html>" +
        "<body> <h1>All our movies will be displayed under this route.</h1> </body>" +
         "</html>";
    } 

    
}
