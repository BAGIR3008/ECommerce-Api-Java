# ECommerce App

A Java-based eCommerce application providing a robust platform for managing products, users, and orders. This application leverages Spring Boot, and MySQL to create a scalable and maintainable solution.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Running Tests](#running-tests)
- [Contributing](#contributing)
- [License](#license)
- [ERD Image](#erd-image)

## Features

- User authentication and authorization
- Product management (CRUD operations)
- Shopping cart and order management
- Payment gateway integration

## Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/BAGIR3008/ECommerce-App-Java.git
   cd ecommerce-app
   ```

2. Set up the database
   - Create a MySQL database:
      ```sql
      CREATE DATABASE db_ecommerce_app;
      ```
   - Update the application.properties file with your MySQL database credentials.

3. Build the project
   ```bash
   mvn clean install
   ```

4. Run the application
   ```bash
   mvn spring-boot:run
   ```

## Configuration
Update `src/main/resources/application.properties` with your database configurations and any other necessary settings.

```properties
spring.application.name=ecommerce-app
spring.datasource.url=jdbc:mysql://localhost:3306/db_ecommerce_app
spring.datasource.username=root
spring.datasource.password=
spring.datasource.type=com.zaxxer.hikari.HikariDataSource
spring.datasource.hikari.minimum-idle=10
spring.datasource.hikari.maximum-pool-size=10
# server.port=8081
```

## Usage
Once the application is running, you can access it at http://localhost:8080.

### Admin Credentials
- Username: admin
- Password: admin123

## API Endpoints
### Authentication
- POST /auth/login - User login
- POST /auth/register - User registration
### Products
- POST /products - Create a new product
- GET /products - List all products
- GET /products/{id} - Get product details
- PUT /products/{id} - Update a product
- DELETE /products/{id} - Delete a product
### Cart
- POST /cart - Add item to cart
- GET /cart - Get all items in cart
- PUT /cart/{id} - Update cart item quantity
- DELETE /cart/{id} - Remove item from cart
### Orders
- POST /orders - Create a new order
- GET /orders - List all orders
- GET /orders/{id} - Get order details

## Running Tests
To run the tests, use the following command:
```bash
mvn test
```

## ERD Image
![ERD Image](./ERD.png)