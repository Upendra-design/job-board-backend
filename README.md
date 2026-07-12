# Lantern Job Board - Backend

A production-ready Job Board Backend built using **Java 17**, **Spring Boot**, **Spring Security**, **JWT Authentication**, **Spring Data JPA**, and **MySQL**. This REST API powers the Lantern Job Board application by providing authentication, job management, company management, saved jobs, and user management features.

---

# Features

- User Registration & Login
- JWT Authentication
- Role-Based Authorization
  - ADMIN
  - EMPLOYER
  - JOB_SEEKER
- Company Management
- Job Posting & Management
- Job Search with Filters
- Save Jobs
- Employer Dashboard
- Pagination & Sorting
- Global Exception Handling
- Request Validation
- RESTful APIs
- Docker Support
- Render Deployment Ready

---

# Tech Stack

- Java 17
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Docker
- Render

---

# Project Structure

```
src
├── main
│   ├── java
│   │   └── com.jobboard.api
│   │       ├── config
│   │       ├── controller
│   │       ├── dto
│   │       ├── exception
│   │       ├── model
│   │       ├── repository
│   │       ├── security
│   │       ├── service
│   │       └── JobBoardApiApplication.java
│   │
│   └── resources
│       ├── application.properties
│       ├── application-dev.properties
│       └── application-prod.properties
│
└── test
```

---

# REST API

## Authentication

| Method | Endpoint |
|---------|----------|
| POST | /api/auth/register |
| POST | /api/auth/login |

---

## Users

| Method | Endpoint |
|---------|----------|
| GET | /api/users/{id} |

---

## Companies

| Method | Endpoint |
|---------|----------|
| GET | /api/companies |
| GET | /api/companies/{id} |
| POST | /api/companies |
| PUT | /api/companies/{id} |
| DELETE | /api/companies/{id} |

---

## Jobs

| Method | Endpoint |
|---------|----------|
| GET | /api/jobs |
| GET | /api/jobs/{id} |
| POST | /api/jobs |
| PUT | /api/jobs/{id} |
| DELETE | /api/jobs/{id} |

Supports:

- Search
- Pagination
- Sorting
- Salary Filter
- Experience Filter
- Job Type Filter
- Category Filter
- Location Filter

---

## Saved Jobs

| Method | Endpoint |
|---------|----------|
| GET | /api/users/{userId}/saved-jobs |
| POST | /api/users/{userId}/saved-jobs/{jobId} |
| DELETE | /api/users/{userId}/saved-jobs/{jobId} |

---

# Authentication

The application uses **JWT Authentication**.

After a successful login, the server returns a JWT token.

Include the token in every secured request.

```
Authorization: Bearer YOUR_JWT_TOKEN
```

---

# Database

Development

- MySQL

Configure your database in:

```
application.properties
```

Example

```
spring.datasource.url=jdbc:mysql://localhost:3306/jobboard
spring.datasource.username=root
spring.datasource.password=yourpassword
```

---

# Running Locally

Clone the repository

```
git clone https://github.com/Upendra-design/job-board-backend.git
```

Go to project

```
cd job-board-backend
```

Build

```
mvn clean install
```

Run

```
mvn spring-boot:run
```

Backend

```
http://localhost:8080
```

---

# Deployment

Backend is deployed using **Render**.

Docker support is included using the provided Dockerfile.

---

# GitHub Repository

https://github.com/Upendra-design/job-board-backend

---
# Frontend Repository

https://github.com/Upendra-design/job-board-frontend
---

# Frontend Live Demo

https://job-board-frontend-gamma-eight.vercel.app

---

# CI/CD

GitHub Actions automatically builds the project on every push to the **main** branch.

Deployment is configured for Render using Docker.

---

# Future Improvements

- Resume Upload
- Email Notifications
- Interview Scheduling
- Company Analytics
- Job Recommendations
- AI Resume Matching
- Admin Dashboard Analytics

---

# Author

**Ramadurgam Upendra**

GitHub

https://github.com/Upendra-design

LinkedIn

https://www.linkedin.com/in/ramadurgam-upendra-a90a08350

---

## License

This project is developed for learning and portfolio purposes.
