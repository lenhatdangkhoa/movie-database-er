package edu.uga.cs4370.Controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

public class CommentsController {

  @GetMapping("/comments")
  public ModelAndView displayComments(Map<String, Object> model) {
    List<Comments> comments = IntStream
      .range(0, 10)
      .mapToObj(i -> generateComment("Comment Title" + i))
      .collect(Collectors.toList());

    model.put("comments", comments);
    return ModelAndView("index", model);
  }
}
