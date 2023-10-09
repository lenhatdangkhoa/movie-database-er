package edu.uga.cs4370.repository;

import edu.uga.cs4370.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<Comment, Long> {}
