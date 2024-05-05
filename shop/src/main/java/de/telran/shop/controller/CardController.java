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

/*
GET  /cards      				retrieve all Cards
GET  /products/:id/card   		retrieve all Cards of a Product
POST  /products/:id/card   		create/add Card for a Product
DELETE  /products/:id/card/:id  delete a Card from a Product by :id
GET  /card/:id/products   		retrieve all Products of a Card
GET  /products     				retrieve all Products
GET  /products/:id    			retrieve a Product with it Cards
DELETE  /products/:id    		delete a Product by :id
GET  /cards/:id     			retrieve a Card by :id

PUT  /cards/:id     			update a Card by :id
DELETE  /cards/:id     			delete a Card by :id

 */

    // GET  /cards      				retrieve all Cards
    // GET http://localhost:8080/cards
    @GetMapping("/cards")
    public ResponseEntity<List<Card>> getAllCards()
    {
        return new ResponseEntity<>(cardRepository.findAll(), HttpStatus.OK);
    }

    // GET  /products/:id/card   		retrieve all Cards of a Product
    // GET http://localhost:8080/products/4/card
    @GetMapping("/products/{productId}/card")
    public ResponseEntity<List<Card>> getAllCardsForAProduct(
            @PathVariable Long productId
    )
    {
        return ResponseEntity.ok(cardRepository.findCardsByProductsId(productId));
    }

    // POST  /products/:id/card   		create/add Card for a Product
    // POST http://localhost:8080/products/:id/card
    // если карты нет, ее нужно создать, сохранить и добавить в нее продукт
    // если карта есть, в продукт нужно добавить эту карту
    @PostMapping("/products/{productId}/card")
    public ResponseEntity<Card> addCardToAProduct(
            @PathVariable Long productId,
            @RequestBody Card cardRequest
    )
    {
        Card card = cardRepository.findById(cardRequest.getId()).orElse(null);
        if(card == null)
            card = cardRepository.save(cardRequest);
        Product product = productsRepository.findById(productId).orElse(null);
        if(product != null)
        {
            product.addToCard(card);
            productsRepository.save(product);
        }
        return ResponseEntity.ok(card);
    }

    // DELETE  http://localhost:8080/products/{productId}/card/{cardId}  delete a Card from a Product by :id
    // DELETE  http://localhost:8080/products/4/card/2
    @DeleteMapping("/products/{productId}/card/{cardId}")
    public ResponseEntity<HttpStatus> deleteCardForAProduct(
            @PathVariable Long productId,
            @PathVariable Long cardId
    )
    {
        Product product = productsRepository.findById(productId).orElse(null);
        if(product != null)
        {
            product.removeProductFromCard(cardId);
            productsRepository.save(product);
        }
        return ResponseEntity.noContent().build();
    }


    // GET  http://localhost:8080/card/{cardId}/products   	retrieve all Products of a Card
    // GET  http://localhost:8080/card/1/products
    @GetMapping("/card/{cardId}/products")
    public ResponseEntity<Iterable<Product>> getProductsForACard(
            @PathVariable Long cardId
    )
    {
        Set<Product> products = null;
        Card card = cardRepository.findById(cardId).orElse(null);
        if(card != null)
        {
            products = card.getProducts();
        }
        return ResponseEntity.ok(products);
    }


    // GET  http://localhost:8080/cards/{cardId}     			retrieve a Card by :id
    // http://localhost:8080/cards/2
    @GetMapping("/cards/{cardId}")
    public ResponseEntity<Card> getCardById(
            @PathVariable Long cardId
    )
    {
        return ResponseEntity.ok(cardRepository.findById(cardId).orElse(null));
    }


    // PUT  http://localhost:8080/cards/{cardId}     			update a Card by :id
    @PutMapping("/cards/{cardId}")
    public ResponseEntity<Card> updateCardById(
            @PathVariable Long cardId,
            @RequestBody Card cardRequest
    )
    {
        // получить из репо карту по cardId
        Card card = cardRepository.findById(cardId).orElse(null);
        if(card != null && cardRequest != null) {
            // поменять в ней название всяв его из cardRequest
            card.setName(cardRequest.getName());
            // сохранить карту в базу данных
            card = cardRepository.save(card);

        }
        // вернуть сохраненную карту
        return ResponseEntity.ok(card);
    }

    // DELETE http://localhost:8080/cards/{cardId}     			delete a Card by :id
    // DELETE http://localhost:8080/cards/2
    @DeleteMapping("/cards/{cardId}")
    public ResponseEntity<HttpStatus> deleteCardById(
            @PathVariable Long cardId
    )
    {
        Card card = cardRepository.findById(cardId).orElse(null);
        if(card != null)
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
