package de.telran.shop.repository;

import de.telran.shop.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findCardsByProductsId(Long productId);
}
