# Time Tracking API

## Project Overview
This project is a **Time Tracking API** built using **Java Spring Boot**. The API allows users to track time spent on various tasks with CRUD functionality and user authentication.

## Features
- User registration and authentication (JWT-based)
- CRUD operations for time entries
- Validation and exception handling
- Swagger API documentation

## Technologies Used
- Java 17
- Spring Boot 3
- Spring Security (JWT Authentication)
- Spring Data JPA
- PostgreSQL Database
- Swagger OpenAPI
- Lombok
- Maven

## Installation and Running the Project

### Prerequisites
- Java 17+
- Maven

### Clone the Repository
```bash
git clone https://github.com/SobachkaKoli/TimeTracking.git
cd TimeTracking
```

### Build the Project
```bash
mvn clean install
```

### Run the Application
```bash
mvn spring-boot:run
```

The application will be available at:
```
http://localhost:8080
```

## API Endpoints

### Authentication
| Method | Endpoint        | Description        |
|--------|----------------|-------------------|
| POST   | /auth/register | Register new user |
| POST   | /auth/login    | Authenticate user |

### Time Tracking
| Method | Endpoint        | Description        |
|--------|----------------|-------------------|
| GET    | /entries       | Get all time entries |
| POST   | /entries       | Create time entry    |
| PUT    | /entries/{id}  | Update time entry    |
| DELETE | /entries/{id}  | Delete time entry    |

## Swagger API Documentation
Swagger UI is available at:
```
http://localhost:8080/swagger-ui/index.html
```

## Author
[Vladyslav Sydiuk](https://github.com/SobachkaKoli)

## License
This project is licensed under the MIT License.

