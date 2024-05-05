package de.telran.shop.controller;

import de.telran.shop.entity.Comment;
import de.telran.shop.entity.Product;
import de.telran.shop.repository.CommentRepository;
import de.telran.shop.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CommentController {

 @Autowired
 private CommentRepository commentRepository;

 @Autowired
    private ProductsRepository productsRepository;
 /*
POST  /products/:id/comments   create new Comment for a Product
GET  /products/:id/comments   retrieve all Comments of a Product
GET  /products/:id            retrieve a Comment by :id
DELETE  /products/:id         delete a Product (and its Comments) by :id

PUT /comments/:id            update a Comment by :id
DELETE /comments/:id         delete a Comment by :id
DELETE /products/:id/comments  delete all Comments of a Product
 */


// POST http://localhost:8080/products/{productId}/comments

    @PostMapping("/products/{productId}/comments")
    public ResponseEntity<Comment> createCommentForAProduct(
        @PathVariable(value = "productId") Long productId,
        @RequestBody Comment comment
    )
    {
        Comment result = null;
        Product product = productsRepository.findById(productId).orElse(null);
        if (product != null && comment != null){
            comment.setProduct(product);
            result = commentRepository.save(comment);
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    // GET http://localhost:8080/products/:id/comments
    // http://localhost:8080/products/1/comments
    @GetMapping("/products/{productId}/comments")
    public ResponseEntity<List<Comment>> getAllCommentsForAFProduct(
            @PathVariable(name = "productId") Long productId
    )
    {
     return ResponseEntity.ok(commentRepository.findByProductId(productId));
    }

    //DELETE http://localhost:8080/products/1/comments
    @GetMapping("/products/:id/comments")
    public ResponseEntity<HttpStatus> getCommentsForAProduct(
            @PathVariable(name = "productId") Long productId
    )
    {
     commentRepository.deleteByProductId(productId);
     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //GET http://locahost:8080/comments/1
    @GetMapping("/comments/{commentId}")
    public ResponseEntity<Comment> getCommentById(
            @PathVariable(name = "commentId") Long commentId
    )
    {
        return ResponseEntity.ok(commentRepository.findById(commentId).orElse(null));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<HttpStatus> deleteCommentById(
            @PathVariable(name = "commentId") Long commentId
    )
    {
       commentRepository.deleteById(commentId);
      // return ResponseEntity.noContent().build();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //PUT http://localhost:8080/comments/{commentId}
    @PutMapping("/comments/{commentId}")
   public ResponseEntity<Comment> updateCommentById(
            @PathVariable Long commentId,
            @RequestBody Comment commentRequest
    )
    {
        Comment comment = commentRepository.findById(commentId).orElse(null);
    if (comment != null && commentRequest != null)
    {
      comment.setContent(commentRequest.getContent());
      comment = commentRepository.save(comment);
    }
     return ResponseEntity.ok(comment);
    }

}

