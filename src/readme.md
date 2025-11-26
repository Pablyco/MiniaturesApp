# 🌟 **Miniatures Management API**

### A clean & scalable Spring Boot REST API for managing clients and sales


  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring boot 3.X"/>
  <img src="https://img.shields.io/badge/Java-17-007396?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 17"/>
  <img src="https://img.shields.io/badge/Build-Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Build Maven"/>
  <img src="https://img.shields.io/badge/Tests-JUnit%205-25A162?style=for-the-badge&logo=junit5&logoColor=white" alt="JUNIT 5"/>


---

> [!NOTE]  
> This project was done for learning and practice.
---

## **Features**

### Clients

* Create, update, delete clients
* List all clients
* Get a client with *all their sales*
* DTO-based request/response structure
* Fully validated inputs

### Sales

* Create sales for any client
* Filter sales by:

    * Date range
    * Price range
    * Client
* DTO-based request/response structure
* Service layer separation

### Documentation :construction: WIP :construction:

* Full **Swagger / OpenAPI** auto-generated docs
* Clear project structure
* DTOs, entities, services fully separated

### Tests :construction: WIP :construction:

* **Mockito** service tests
* **JUnit 5** unit tests
* **MockMvc** controller tests

---

# Project Structure

```
src/main/java/com.example.miniatures
├── controller
├── dto
│   ├── miniatureClient
│   └── miniatureSale
├── exception
├── model
│   ├── enums
├── repository
├── service
└── MiniaturesApplication.java
```

---

# API Overview

## Client Endpoints

| Method | Endpoint              | Description        |
| ------ | --------------------- |--------------------|
| POST   | `/clients`            | Create new client  |
| GET    | `/clients`            | List all clients   |
| GET    | `/clients/{id}`       | Get client info    |
| GET    | `/clients/{id}/sales` | Get client sales   |
| PUT    | `/clients/{id}`       | Update client      |
| DELETE | `/clients/{id}`       | Delete client      |

---

## Sales Endpoints

| Method | Endpoint        | Description          |
| ------ |-----------------|----------------------|
| POST   | `/sales`        | Create new sale      |
| GET    | `/sales`        | List or filter sales |
| GET    | `/sales/latest` | List latest 10 sales |
| GET    | `/sales/{id}`   | Get sale by ID       |
| PUT    | `/clients/{id}` | Update sale          |
| DELETE | `/clients/{id}` | Delete sale          |

---

# Sales Filtering Examples

### Basic

```
GET /sales?clientId=1
```

### Full filters

```
GET /sales?clientId=1&minPrice=20&maxPrice=200&startDate=2024-01-01&endDate=2024-12-31
```

---

# Running Tests

```bash
mvn test
```

---

# Launching the App

```bash
mvn spring-boot:run
```

Once running, open Swagger:

👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

---

# Database Config (H2 Example)

```properties
spring.datasource.url=jdbc:h2:mem:miniatures
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
```

H2 console:

```
http://localhost:8080/h2-console
```

---

# Example JSON

### Create Client

```json
{
  "name": "John Example",
  "email": "john@example.com",
  "phone": "555-1234"
}
```

### Create Sale

```json
{
  "name": "Space Marine",
  "price": 20,
  "date": "2025-01-11",
  "scale": "MEDIUM_100MM",
  "type": "WARHAMMER",
  "clientId":1
}
```

---

# Tech Stack

| Layer    | Technology        |
| -------- |-------------------|
| Backend  | Spring Boot 3     |
| Language | Java 17           |
| Docs     | Springdoc OpenAPI |
| Database | H2                |
| Build    | Maven             |
| Tests    | JUnit 5 + Mockito |


---

# 📄 License

MIT License — free to use, modify, and distribute.

---

Pablyco :)

