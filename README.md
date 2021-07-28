# bookstore

## Requirements
- JDK 1.8 or 
- Maven 3.2+
- MySQL
- Redis

## Database Setup 
Create database
		
    create database book_store;        
Create user and grant privileges
		
    create user 'bookstoreadmin' identified by 'password';
    grant all on book_store.* to 'bookstoreadmin';
Create tables in book_store database

    create database book_store;

- book

      CREATE TABLE `book` (
        `id` bigint NOT NULL,
        `author` varchar(255) DEFAULT NULL,
        `is_recommended` bit(1) DEFAULT NULL,
        `name` varchar(255) DEFAULT NULL,
        `price` decimal(19,2) DEFAULT NULL,
        PRIMARY KEY (`id`)
      );
        
- user

      CREATE TABLE `user` (
        `id` bigint NOT NULL,
        `date_of_birth` varchar(255) NOT NULL,
        `password` varchar(255) NOT NULL,
        `username` varchar(255) NOT NULL,
        PRIMARY KEY (`id`),
        UNIQUE KEY (`username`)
      );
          
- order_book - for map data between user and book
          
      CREATE TABLE `order_book` (
        `user_id` bigint NOT NULL,
        `book_id` bigint NOT NULL,
        CONSTRAINT FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
        CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
      );
          
- hibernate_sequence - sequence for user id and insert value 0 

      CREATE TABLE `hibernate_sequence` (
        `next_val` bigint DEFAULT NULL
      );

    <!-- -->

      INSERT INTO `hibernate_sequence` VALUES (0);

config mysql connection in src\main\resources\application.properties

    . . .
    #mysql server url
    spring.datasource.url=jdbc:mysql://localhost:3306/book_store
    #username
    spring.datasource.username=bookstoreadmin
    #password
    spring.datasource.password=password	
    . . .

## Redis Setup
Start redis server

    redis-server
config redis connection in src\main\resources\application.properties

    # Redis Config
    spring.redis.host=localhost
    spring.redis.port=6379
    server.servlet.session.timeout=600

## Start server
start spring boot server

    ./mvnw spring-boot:run


## Book Store API
- #### GET /books

  Get a list of books in book store

      curl -i --request GET "http://localhost:8080/books"
  <!-- -->

      HTTP/1.1 200
      X-Content-Type-Options: nosniff
      X-XSS-Protection: 1; mode=block
      Cache-Control: no-cache, no-store, max-age=0, must-revalidate
      Pragma: no-cache
      Expires: 0
      X-Frame-Options: DENY
      Content-Type: application/json
      Transfer-Encoding: chunked
      Date: Wed, 28 Jul 2021 07:20:55 GMT

      {"books":[{"id":5,"name":"An American Princess: The Many Lives of Allene Tew","author":"Annejet van der Zijl, Michele Hutchison","price":149.00,"is_recommended":true}
      . . .

- #### POST /users

  Register new user

      curl -i --request POST "http://localhost:8080/users" ^
      --header "Content-Type: application/json" ^
      --data-raw "{\"username\": \"john.doe\",\"password\": \"thisismysecret\",\"date_of_birth\": \"02/11/1985\"}"

- #### POST /login

      curl -i --request POST "http://localhost:8080/login" ^
      --header "Content-Type: application/json" ^
      --data-raw "{
        \"username\": \"john.doe\",
        \"password\": \"thisismysecret\"
      }"
    
    After successful login you will receive cookie session
    You will need to set the cookie session you received in the request header to be used to verify your identity

	Example:

      HTTP/1.1 200
      X-Content-Type-Options: nosniff
      X-XSS-Protection: 1; mode=block
      Cache-Control: no-cache, no-store, max-age=0, must-revalidate
      Pragma: no-cache
      Expires: 0
      X-Frame-Options: DENY
      Set-Cookie: SESSION=NDBiZTNkMDktZWJlMy00NWE0LTliNDAtYzdhNDNmOTRjYTE4; Path=/; HttpOnly; SameSite=Lax
      Content-Length: 0
      Date: Wed, 28 Jul 2021 06:59:19 GMT

      your session is "NDBiZTNkMDktZWJlMy00NWE0LTliNDAtYzdhNDNmOTRjYTE4"

- #### POST /users/orders (Login required)

  For order books

      curl -i --request POST "http://localhost:8080/users/orders" ^
      --header "Content-Type: application/json" ^
      --header "Cookie: SESSION={your session}" ^
      --data-raw "{\"orders\": [4, 10]}"

    You will receive a total price of books you ordered

      HTTP/1.1 200
      X-Content-Type-Options: nosniff
      X-XSS-Protection: 1; mode=block
      Cache-Control: no-cache, no-store, max-age=0, must-revalidate
      Pragma: no-cache
      Expires: 0
      X-Frame-Options: DENY
      Content-Type: application/json
      Transfer-Encoding: chunked
      Date: Wed, 28 Jul 2021 07:13:04 GMT

      {"price":814.16}

- #### GET /users/ (Login required)

      curl -i --request GET "http://localhost:8080/users" ^
      --header "Cookie: SESSION={your session}"

    You will receive a user infomation and ordered book

      HTTP/1.1 200
      X-Content-Type-Options: nosniff
      X-XSS-Protection: 1; mode=block
      Cache-Control: no-cache, no-store, max-age=0, must-revalidate
      Pragma: no-cache
      Expires: 0
      X-Frame-Options: DENY
      Content-Type: application/json
      Transfer-Encoding: chunked
      Date: Wed, 28 Jul 2021 07:24:21 GMT

      {"name":"john","surname":"doe","books":[4,10],"date_of_birth":"02/11/1985"}
      
- #### DELETE /users/ (Login required)

  Remove user account and ordered history 

      curl -i --request DELETE "http://localhost:8080/users" ^
      --header "Cookie: SESSION={your session}"
    
