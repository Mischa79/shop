package de.telran.shop.repository;

import de.telran.shop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
// CrudRepository - уже имеющийся в spring репозитори
// должен быть параметризован типом Entity, который через него будет сохраняться в таблицу бд
// и типом первичного ключа этой таблицы
public interface ProductsRepository extends CrudRepository<Product, Long> {
    // https://www.baeldung.com/spring-jpa-like-queries
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    Product findByNameLike(String string);
    Iterable<Product> findByPriceBetween(BigDecimal from, BigDecimal to);

    // у нас есть колонки
    // name, isactive, price

    // найти все товары у которых с определенным статусом isActive и у которых цена больше какой-то
    // JPQL
    @Query("select p from Product p where p.isActive = :isActive and p.price > :priceFrom")
    Iterable<Product> getAllProductsWithActiveStateAndPriceGreaterThan(boolean isActive, BigDecimal priceFrom);

    // напишите функцию getProductsWithPricesBetween(priceFrom, priceTo)
    // возвращающую список продуктов с ценами в указанном диапазоне
    // JPQL
    // @Query("select p from Product p where p.price > :priceFrom and p.price < :priceTo")
    // SQL
    @Query(
            value = "select * from PRODUCTS where PRODUCT_PRICE BETWEEN :priceFrom AND :priceTo",
            nativeQuery = true // запрос внутри value написан на SQL
    )
    Iterable<Product> getProductsWithPricesBetween(BigDecimal priceFrom, BigDecimal priceTo);

    // Paging - просим базу данных возвращать нам список товаров страницами

    @Query("select p from Product p ORDER BY p.id")
    Page<Product> getPage(Pageable pageable); // Pageable какая страница, сколько товаров на странице и т.п.


}
