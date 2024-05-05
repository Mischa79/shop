package de.telran.shop.repository;

import de.telran.shop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductsRepository extends CrudRepository<Product, Long> {
    //https://www.boeldung.com/spring-jpa-like-queries
    Product findByNameLike(String string);
    Iterable<Product> findByPriceBetween(BigDecimal from, BigDecimal to);

    @Query("select p from Product p where p.isActive = :isActive and p.price > :priceFrom")
    Iterable<Product> getAllProductsWithActiveStateAndPriceGreaterThan(boolean isActive, BigDecimal priceFrom);

   // @Query("select p from Product p where p.price > :priceFrom and p.price < :priceTo")
    @Query(
            value = "select * from PRODUCTS where PRODUCT_PRICE BETWEEN :priceFrom AND :priceTo",
            nativeQuery = true
    )
    Iterable<Product> getProductsWithPricesBetween(BigDecimal priceFrom, BigDecimal priceTo);

    @Query("select p from Product p ORDER BY p.id")
    Page<Product> getPage(Pageable pageable);



}
