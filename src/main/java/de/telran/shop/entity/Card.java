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

    private String name;

    @ManyToMany
            (
              fetch = FetchType.LAZY,
                    cascade = {
                      CascadeType.ALL,
                            CascadeType.MERGE
                    },
                    mappedBy = "cards"
            )
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

}
