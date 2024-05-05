package de.telran.shop.controller;

import de.telran.shop.entity.Card;
import de.telran.shop.entity.Product;
import de.telran.shop.repository.CardRepository;
import de.telran.shop.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class CardController {
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private CardRepository cardRepository;

    //GET http://localhost:8080/cards
    @GetMapping("/cards")
    public ResponseEntity<List<Card>> getAllCards()
    {
      return new ResponseEntity<>(cardRepository.findAll(), HttpStatus.OK);
    }

    //GET http://localhost:8080/products/4/card
    @GetMapping("/products/{productId}/card")
    public ResponseEntity<List<Card>> getAllCardsForAProduct(
            @PathVariable Long productId
    )
    {
     return  ResponseEntity.ok(cardRepository.findCardsByProductsId(productId));
    }

    //POST http://localhost:8080/products/:id/card

    @PostMapping("/products/{productId}/card")
    public ResponseEntity<Card> addCardToAProduct(
            @PathVariable Long productId,
            @RequestBody Card cardRequest
    )
    {
        Card card = cardRepository.findById(cardRequest.getId()).orElse(null);
        if (card == null)
            card = cardRepository.save(cardRequest);
        Product product = productsRepository.findById(productId).orElse(null);
        if (product != null)
        {
            product.addToCard(card);
            productsRepository.save(product);
        }
        return ResponseEntity.ok(card);
    }

    // DELETE http://localhost:8080/products/{productId}/card/{cardId}
     // http://localhost:8080/products/4/card/2
    @DeleteMapping("/products/{productId}/card/{cardId}")
    public ResponseEntity<HttpStatus> deleteCardForAProduct(
          @PathVariable Long productId,
          @PathVariable Long cardId
    )
    {
        Product product = null;
        product = productsRepository.findById(productId).orElse(null);
        if (product != null)
        {
            product.removeProductFromCard(cardId);
            productsRepository.save(product);
        }
        return ResponseEntity.noContent().build();
    }

    //GET http://localhost:8080/card/{cardId}/products
   // http://localhost:8080/card/1/products
    @GetMapping("/card/{cardId}/products")
    public ResponseEntity<Iterable<Product>> getProductsForACard(
            @PathVariable Long cardId
    )
    {
        Set<Product> products = null;
        Card card = cardRepository.findById(cardId).orElse(null);
        if (card != null)
        {
           products = card.getProducts();
        }
        return ResponseEntity.ok(products);
    }

    //GET http://localhost:8080/cards/{cardId}
    @GetMapping("/cards/{cardId}")
    public ResponseEntity<Card> getCardById(
            @PathVariable Long cardId
    )
    {
        return ResponseEntity.ok(cardRepository.findById(cardId).orElse(null));
    }

    //PUT  http://localhost:8080/cards/{cardId}
    @PutMapping("/cards/{cardId}")
    public ResponseEntity<Card> updateCadById(
            @PathVariable Long cardId,
            @RequestBody Card cardRequest
    )
    {
        Card card = cardRepository.findById(cardId).orElse(null);
        if (card != null && cardRequest != null)
        {
            card.setName(cardRequest.getName());
            card = cardRepository.save(card);
        }
        return ResponseEntity.ok(card);
    }

    //DELETE http:localhost:8080/cards/{cardId}
    //DELETE http:localhost:8080/cards/2
    @DeleteMapping("/cards/{cardId}")
    public ResponseEntity<HttpStatus> deleteCardById(
            @PathVariable Long cardId
    )
    {
        Card card = cardRepository.findById(cardId).orElse(null);
        if (card != null)
        {
            Set<Product> copy = new HashSet<>(card.getProducts());
            copy.forEach(
                    product -> {
                        product.removeProductFromCard(cardId);
                        productsRepository.save(product);
                    }
            );
            cardRepository.deleteById(cardId);
        }
        return ResponseEntity.noContent().build();
    }
}
