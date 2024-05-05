package de.telran.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "comments") // название таблицы в базе данных
@Data // @Getter @Setter @ToString
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // база данных сама будет генерировать
    private Long id;

    @Column(name = "content") // если вдруг колонка в талице бд отличается от названия поля
    private String content;

    @ManyToOne( // создает в таблице comments внешний ключ в таблицу products
            fetch = FetchType.LAZY // не поднимать продукт, соответствующий комментарию, из таблицы базы данных
            , optional = false // для каждого коммента должен быть продукт
    )
    @OnDelete // что делать когда продукт удаляется
            (action = OnDeleteAction.CASCADE) // автоматически удалять комменты к удаляемому продукту
    @JoinColumn(name = "product_id", nullable = false) // как будет называться колонка внешнего ключа
    @JsonIgnore // не добавлять эту колонку в json
    private Product product;

    public Comment(String content, Product product) {
        this.content = content;
        this.product = product;
    }
}
