# Blog API Application

This project is a Spring Boot-based REST API application for a blogging platform. It includes functionalities for managing users, categories, posts, comments, and roles. The application also includes JWT-based authentication.

## Table of Contents

- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Entities](#entities)
- [Dependencies](#dependencies)
- [License](#license)

## Getting Started

To get a local copy of the project up and running, follow the steps below.

### Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher
- MySQL 5.7 or higher

### Installation

1. Clone the repository
   ```bash
   git clone https://github.com/yourusername/blog-api.git
   cd blog-api
   ```
2. Update the `application.properties` file in the `src/main/resources` directory with your MySQL configuration
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/blogdb
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```
3. Build the project
   ```bash
   mvn clean install
   ```

### Running the Application

You can run the application using the following command:
```bash
mvn spring-boot:run
```

The application will start on port 8080 by default.

## API Endpoints

### Authentication Controller
- `POST /api/v1/auth/login` - Authenticate user and get JWT token
- `POST /api/v1/auth/register` - Register a new user
- `GET /api/v1/auth/current-user` - Get the currently authenticated user

### User Controller
- `POST /api/users` - Create a new user
- `PUT /api/users/{user_id}` - Update an existing user
- `DELETE /api/users/{user_id}` - Delete a user
- `GET /api/users` - Get all users with pagination
- `GET /api/users/{user_id}` - Get a single user by ID

### Category Controller
- `POST /api/categories` - Create a new category
- `PUT /api/categories/{categoryId}` - Update an existing category
- `DELETE /api/categories/{categoryId}` - Delete a category
- `GET /api/categories` - Get all categories with pagination
- `GET /api/categories/{categoryId}` - Get a single category by ID

### Post Controller
- `POST /api/users/{user_id}/categories/{categoryId}/posts` - Create a new post
- `PUT /api/posts/{postId}` - Update an existing post
- `DELETE /api/posts/{postId}` - Delete a post
- `GET /api/posts` - Get all posts with pagination
- `GET /api/posts/{postId}` - Get a single post by ID
- `GET /api/categories/{categoryId}/posts` - Get all posts by category
- `GET /api/users/{user_Id}/posts` - Get all posts by user
- `GET /api/posts/search/{keywords}` - Search posts by title
- `POST /api/post/image/upload/{postId}` - Upload an image for a post
- `GET /api/post/image/{imageName}` - Download an image by name

## Entities

### User
- id
- name
- email
- password
- roles

### Category
- id
- name
- description

### Post
- id
- title
- content
- image
- createdDate
- category
- user

### Comment
- id
- content
- post
- user

### Role
- id
- name

## Dependencies

The project uses the following dependencies:

- **Spring Boot Starter Data JPA**
- **Spring Boot Starter Web**
- **Spring Boot Starter Security**
- **Spring Boot Starter Validation**
- **Spring Boot DevTools**
- **Spring Boot Starter Test**
- **MySQL Connector Java**
- **Lombok**
- **MapStruct**
