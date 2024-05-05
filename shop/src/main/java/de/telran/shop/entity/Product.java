package de.telran.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter  // сгенерирует геттеры для всех полей
@Setter // сгенерирует сеттеры для всех полей
@ToString // сгенерировать toString
@NoArgsConstructor // сгенерировать безаргументный конструктор
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // таблицей будет автоматически генерироваться
    // возрастающий long для каждой вставляемой строки
    private Long id;

    @Column(name = "PRODUCT_NAME", length = 50, nullable = false, unique = false)
    // максимальная длина колонки 50 символов
    // можно хранить NULL
    // не нужно создавать индекс для проверки уникальности хранимых данных
    private String name;

    @Column(name = "PRODUCT_PRICE", columnDefinition = " Decimal(10,2) default '0.00' ", nullable = false)
    // можно явно писать часть sql кода для генерации колонки указывая параметры точности и значение по-умолчанию
    // максимум 10 цифр и 2 цифры после запятой
    private BigDecimal price;

    @Column(name = "PRODUCT_IS_ACTIVE", columnDefinition = "Boolean default 'true' ", nullable = false)
    private boolean isActive;

    public Product(String name, BigDecimal price, boolean isActive) {
        this.name = name;
        this.price = price;
        this.isActive = isActive;
    }

    @ManyToMany
            (
                    fetch = FetchType.LAZY,
                    cascade = {
                            CascadeType.ALL,
                            CascadeType.MERGE
                    }
            )
    @JoinTable
            (
                    name = "product_card",
                    joinColumns = {@JoinColumn(name = "product_id")},
                    inverseJoinColumns = {@JoinColumn(name = "card_id")}
            )
    private Set<Card> cards = new HashSet<>();

    // добавление продукта в карту
    public void addToCard(Card card)
    {
        cards.add(card); // добавляем карту с список
        card.getProducts().add(this); // добавляем себя в список продуктов в карте
    }

    // удаление продукта из карты
    public void removeProductFromCard(Long cardId)
    {
        Card card = cards.stream()
                .filter(c -> c.getId().equals(cardId))
                .findFirst().orElse(null);
        if(card != null)
        {
            cards.remove(card);
            card.getProducts().remove(this);
        }
    }
}
