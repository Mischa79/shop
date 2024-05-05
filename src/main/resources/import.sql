-- import.sql

INSERT INTO products (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IS_ACTIVE) values ('Coca-Cola 0.33 alluminium', 0.86, 'true');
INSERT INTO products (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IS_ACTIVE) values ('Red Bull 0.5 alluminium', 2.05, 'true');
INSERT INTO products (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IS_ACTIVE) values ('Neslte Kit-Kat white', 1.02, 'false');
INSERT INTO products (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IS_ACTIVE) values ('Raviolli with cheese', 0.99, 'true');
INSERT INTO products (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IS_ACTIVE) values ('Goat cheese', 1.4, 'true');
INSERT INTO products (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IS_ACTIVE) values ('Safety Matches', 0.04, 'false');
INSERT INTO products (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IS_ACTIVE) values ('Calamari burger', 0.55, 'true');

INSERT INTO products (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IS_ACTIVE) values ('Salmon sandwich', 2.44, 'true');
INSERT INTO products (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IS_ACTIVE) values ('Wasabi', 1.33, 'false');
INSERT INTO products (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IS_ACTIVE) values ('Chicken Rice', 1.77, 'true');
INSERT INTO products (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IS_ACTIVE) values ('Salami with pepper', 2.01, 'true');
INSERT INTO products (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IS_ACTIVE) values ('Shrimp coctail', 11.22, 'false');
INSERT INTO products (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IS_ACTIVE) values ('Salmon rolls', 8.71, 'true');
INSERT INTO products (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IS_ACTIVE) values ('Baguette with coriander', 2.31, 'true');
INSERT INTO products (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IS_ACTIVE) values ('Pork sausages', 2.44, 'false');

insert into COMMENTS (CONTENT, PRODUCT_ID) values ('Its my favorite product', 1);
insert into COMMENTS (CONTENT, PRODUCT_ID) values ('I like it very much', 2);
insert into COMMENTS (CONTENT, PRODUCT_ID) values ('Red Bull is best', 2);

insert into cards(name) values ('My Favorite Products'); -- 1
insert into cards(name) values ('My Cristmas Products'); -- 2

insert into PRODUCT_CARD(product_id, card_id) values (1, 1);
insert into PRODUCT_CARD(product_id, card_id) values (8, 1);
insert into PRODUCT_CARD(product_id, card_id) values (4, 1);
insert into PRODUCT_CARD(product_id, card_id) values (10, 1);
insert into PRODUCT_CARD(product_id, card_id) values (2, 1);

insert into PRODUCT_CARD(product_id, card_id) values (3, 2);
insert into PRODUCT_CARD(product_id, card_id) values (4, 2);