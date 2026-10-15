# Online Store Assessment

A Spring Boot REST API for a simple online store. It serves products and categories, supports filtering, and handles checkout with credit card validation. Data is stored in an in-memory H2 database that is filled with sample products when the application starts.

This is the **`main` branch**, which holds the base version of the project. Later enhancements live on separate branches (see [Branches](#branches)).

> The starter code for this project comes from the Top Developer Academy course by Michael Pogrebinsky ([topdeveloperacademy.com](https://www.topdeveloperacademy.com)).

## Tech stack

- Java 11 or later
- Spring Boot 2.5.0 (Spring Web, Spring Data JPA)
- H2 in-memory database
- Maven

## Features

- Product catalog organised into categories
- Product listing by category, and "deals of the day"
- Product filtering by category, price range and description
- Checkout with credit card validation (invalid and stolen card checks)
- Order quantity limit, configurable in `application.properties`
- Global exception handling

## Project structure

```
src/main/java/online/store
├── controllers     HomePageController, CheckoutController, GlobalControllerExceptionHandler
├── exceptions      CreditCardValidationException
├── model           Product, ProductCategory, Order, CheckoutRequest
│   └── wrappers    ProductsWrapper
├── repositories    ProductRepository, ProductCategoryRepository, OrderRepository
├── services        ProductsService, OrdersService, CreditCardValidationService
└── Application.java
```

## Getting started

### Prerequisites

- JDK 11 or later
- Maven 3.6 or later

### Run the application

```bash
git clone https://github.com/Akhila-Chatti/Online_Store_Assessment.git
cd Online_Store_Assessment
mvn clean compile
mvn spring-boot:run
```

The API runs at `http://localhost:8080`. Sample products and categories are loaded into the H2 database at startup.

## API endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/categories` | Comma-separated list of categories |
| GET | `/deals_of_the_day/{number_of_products}` | At most N products |
| GET | `/products` | All products, optionally filtered with `?category=` |
| GET | `/products/filter` | Filter by `category`, `min_price`, `max_price` and `description` |
| POST | `/checkout` | Place an order |

### Examples

```bash
curl http://localhost:8080/categories
curl http://localhost:8080/products
curl "http://localhost:8080/products?category=electronics"
curl "http://localhost:8080/products/filter?category=electronics&min_price=90"
curl http://localhost:8080/deals_of_the_day/3
```

### Checkout

```bash
curl -X POST http://localhost:8080/checkout \
  -H "Content-Type: application/json" \
  -d '{
        "firstName": "Jane",
        "lastName": "Doe",
        "email": "jane@example.com",
        "shippingAddress": "1 Main St",
        "creditCard": "4111111111111111",
        "products": [ { "productId": 9, "quantity": 2 } ]
      }'
```

Use real product IDs from `GET /products`. IDs depend on how the database generates them, so they may not start at 1.

Checkout rules:

- The credit card must be exactly 16 digits and must not be on the stolen cards list.
- First name, last name and credit card are required.
- The total number of items in one order is limited by `products.service.max-number-of-items` (default 25).

## Branches

| Branch | Content |
|---|---|
| `main` | Base project (this branch) |
| [`ProductDto-using-Mapstruct`](https://github.com/Akhila-Chatti/Online_Store_Assessment/tree/ProductDto-using-Mapstruct) | MapStruct and DTOs |
| [`Pagination/with-page-and-limit`](https://github.com/Akhila-Chatti/Online_Store_Assessment/tree/Pagination/with-page-and-limit) | Pagination with `page` and `limit` |
| [`JPQL-with-joins`](https://github.com/Akhila-Chatti/Online_Store_Assessment/tree/JPQL-with-joins) | JPQL queries, including a 2-join query |

## Notes

This is a learning project, not production-ready.

- Credit card numbers should never be stored or logged in a real system. Use a payment provider and store its token instead.
- The H2 database is in-memory, so all data is lost when the application stops.

## Author

Akhila Chatti
