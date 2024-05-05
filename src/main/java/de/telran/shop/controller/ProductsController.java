package de.telran.shop.controller;

import de.telran.shop.entity.Product;
import de.telran.shop.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductsController {

  //  @Autowired
   // private ProductsRepository repository;

    private ProductsRepository repository;
    @Autowired

    public ProductsController(ProductsRepository repository) {
        this.repository = repository;
    }

    //GET  http://localhost:8080/products
    @GetMapping("/products")
    public ResponseEntity<Iterable<Product>> getAll()
    {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PutMapping("/products")
    public ResponseEntity<Product> createProduct(
          /* @RequestBody*/ Product productRequest
    )
    {
        productRequest = repository.save(productRequest);
        return new ResponseEntity<>(productRequest, HttpStatus.CREATED);
    }
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<HttpStatus> deleteProductById(
            @PathVariable(name = "productId") Long productId
    )
    {
        repository.deleteById(productId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //GET http://localhost:8080/products/find?priceFrom=5&active=true
    @GetMapping("/products/find")
    public ResponseEntity<Iterable<Product>> findByActiveAndPrice(
            @RequestParam(name = "priceFrom",defaultValue = "0.0")BigDecimal priceFrom,
            @RequestParam(name = "active", defaultValue = "true") boolean isActive
            )
    {
        return new ResponseEntity<>(
      repository.getAllProductsWithActiveStateAndPriceGreaterThan(isActive, priceFrom),
                HttpStatus.OK
        );
    }
    //GET http://localhost:8080/products/price?priceFrom=2&priceTo=8
    @GetMapping("/products/price")
    public ResponseEntity<Iterable<Product>> findProductsWithPriceBetween(
            @RequestParam(name = "priceFrom",defaultValue = "0.0") BigDecimal priceFrom,
            @RequestParam(name = "priceTo", defaultValue = "2.0") BigDecimal priceTo
    )
    {
        return new ResponseEntity<>(
                repository.getProductsWithPricesBetween(priceFrom, priceTo ),
                HttpStatus.OK
        );
    }

    //GET http://localhost:8080/products/page?size=5&page=0
    @GetMapping("/products/page")
    public ResponseEntity<List<Product>> getPage(
            @RequestParam(name = "size", defaultValue = "5") int pageSize,
            @RequestParam(name = "page", defaultValue = "0") int pageNumber
    )
    {

        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNumber);
        return new ResponseEntity<>(
                repository.getPage(pageable).get().collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

}
