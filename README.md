### README

---

# Author API Application

This project provides two RESTful API endpoints to search for authors and their works. It integrates with the Open Library Authors API and uses MySQL for local data storage.

## Features

1. **Search Authors by Name**:
    - Searches for an author in the local database.
    - If not found, fetches the author data from the Open Library API and saves it locally.

2. **Search Works by Author ID**:
    - Retrieves works by an author from the local database.
    - If not found, fetches the works data from the Open Library API and saves it locally.

---

## Prerequisites

- Java 17
- MySQL 8.x
- Maven 3.8.x
- Liquibase
- Internet connection (to access the Open Library API)

---

## Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/asatbekDeveloper/author-api.git
   cd author-api
   ```

2. **Database Configuration**:
   Update `src/main/resources/application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/author_api
       username: root
       password: your_password
     liquibase:
       change-log: classpath:db/changelog/db.changelog-master.xml
     jpa:
       hibernate:
         ddl-auto: none
   ```

3. **Run Liquibase Migrations**:
   ```bash
   mvn liquibase:update
   ```

4. **Build and Run the Application**:
   ```bash
   mvn spring-boot:run
   ```

5. **API Endpoints**:
    - Search Author: `GET /api/v1/authors/search?name={author_name}`
    - Search Works: `GET /api/v1/authors/{authorId}/works`

---

## Testing the Application

- Use tools like Postman or cURL to test the endpoints.
- Example:
  ```bash
  curl -X GET 'http://localhost:8080/api/v1/authors/search?name=rowling'
  curl -X GET 'http://localhost:8080/api/v1/authors/OL1817979A/works'
  ```

---