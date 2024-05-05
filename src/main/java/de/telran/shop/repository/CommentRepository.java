package de.telran.shop.repository;

import de.telran.shop.entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    List<Comment> findByProductId(Long productId);


    @Transactional
    void deleteByProductId(Long productId);
}
