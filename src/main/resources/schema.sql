-- schema.sql

-- будет автоматически выполнен при старте приложения если
-- настрока в application.properties
-- spring.sql.init.mode=embedded и база данных H2
-- либо
-- spring.sql.init.mode=always

create TABLE COUNTRIES (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);


create TABLE BOOK (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);