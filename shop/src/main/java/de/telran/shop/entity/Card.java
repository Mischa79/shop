package de.telran.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cards")
@Data
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // название заказа

    @ManyToMany
            (
                    fetch = FetchType.LAZY,
                    cascade = {
                            CascadeType.ALL, // удалять даные в промежуточной таблице при удалении карты
                            CascadeType.MERGE
                    },
                    mappedBy = "cards" // поле карты в сущности продуктов
            )
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

}
