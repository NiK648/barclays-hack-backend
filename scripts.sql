CREATE TABLE books (
   id INTEGER NOT NULL,
   title VARCHAR(255) NOT NULL,
   authors VARCHAR(255) NOT NULL,
   average_rating REAL NOT NULL,
   isbn INTEGER NOT NULL,
   language VARCHAR(20) NOT NULL,
   ratings_count INTEGER NOT NULL DEFAULT 0,
   price INTEGER NOT NULL DEFAULT 0,
   image VARCHAR(255) NOT NULL,
   PRIMARY KEY(id)
);

select * from books;
select count(*) from books;
truncate table books;
drop table books;

CREATE TABLE users (
   id INT GENERATED ALWAYS AS IDENTITY,
   username VARCHAR(255) NOT NULL UNIQUE,
   password VARCHAR(255) NOT NULL,
   name VARCHAR(255) NOT NULL,
   email  VARCHAR(255) NOT NULL,
   phone  VARCHAR(255) NOT NULL,
   PRIMARY KEY(id)
);

truncate table users;
drop table users;

CREATE TABLE orders (
   id INT GENERATED ALWAYS AS IDENTITY,
   username VARCHAR(255) NOT NULL,
   total REAL NOT NULL,
   status VARCHAR(100) NOT NULL,
   payment_id VARCHAR(255),
   transaction_id VARCHAR(255),
   PRIMARY KEY(id)
);

CREATE TABLE orderItems (
   id INT GENERATED ALWAYS AS IDENTITY,
   orderId INT NOT NULL,
   bookId INT NOT NULL,
   title VARCHAR(255) NOT NULL,
   quantity INT NOT NULL,
   price INT NOT NULL,
   PRIMARY KEY(id)
);