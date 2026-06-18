# Food Ordering System API

A Spring Boot RESTful API designed to manage backend operations for a food ordering platform. This project follows a clean, layered architecture to deliver structured, validated, and maintainable backend endpoints.

---

## Technical Stack
* **Java:** JDK 21
* **Framework:** Spring Boot 3.x
* **Data Access:** Spring Data JPA / Hibernate
* **Database:** MySQL
* **Dependency Management:** Maven
* **Testing:** Postman

---

## Package Structure

The project is organized into modular packages to enforce a clean separation of concerns:

* **`controller`**: The entry point for incoming HTTP requests. It routes endpoints and handles request/response bodies.
* **`service`**: Contains the core business logic and processes data between controllers and repositories.
* **`repository`**: Handles direct communication and data access operations with the MySQL database via Spring Data JPA.
* **`entity`**: Defines the database models mapped directly to MySQL tables.
* **`dto` (Data Transfer Object)**: Structures decoupled data layouts safely tailored for API client exposure without exposing database entities directly.
* **`config`**: Holds application-wide configurations, security parameters, or third-party bean setups.
* **`exception`**: Centralizes custom exceptions (e.g., `CategoryNotFoundException`) for structured error handling.

---

## Endpoints

| Method | URL | Body | Description | Status Codes |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/api/categories` | _None_ | Retrieve all food categories | `200 OK` |
| **GET** | `/api/categories/{id}` | _None_ | Fetch a specific category by its ID | `200 OK` / `404 Not Found` |
| **POST** | `/api/categories` | `{ "name": "string" }` | Create a new food category with input validation | `211 Created` / `400 Bad Request` |
| **PUT** | `/api/categories/{id}` | `{ "name": "string" }` | Update an existing category name by ID | `200 OK` / `400 Bad` / `404 Missing` |
| **DELETE** | `/api/categories/{id}` | _None_ | Permanently delete a category by ID | `204 No Content` / `404 Missing` |

---

## Validation Rules
To safeguard data integrity, the `CategoryDto` enforces strict rules on incoming data paths:
* **Category Name**: Cannot be blank or empty (`@NotBlank`).
* **Category Name Length**: Restricted strictly between **2 to 50 characters** (`@Size`). Violation returns a `400 Bad Request` status code.

---

## Setup & Running Locally

### 1. Prerequisites
Ensure you have the following installed locally:
* Java JDK 21
* MySQL Server & Workbench / DBeaver
* Git

### 2. Database Configuration
Create a database named `food_ordering_db`. Modify your local `src/main/resources/application.properties` file credentials to point to your local database setup:

```properties
server.port=8085
spring.datasource.url=jdbc:mysql://localhost:3306/food_ordering_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update