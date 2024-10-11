# URL Shortener Service

## Overview

This URL shortener service is a Spring Boot application that allows users to shorten long URLs into more manageable, shorter URLs. When accessing these short URLs, users are redirected to the original long URL. The service is containerized using Docker and can be easily deployed using Docker Compose.

## Features

- Shorten long URLs to create easily shareable short links
- Redirect from short URLs to their original long URLs
- Containerized application for easy deployment
- Uses H2 locally and PostgreSQL prod, for persistent storage (with option to switch to MySQL)
- Implements Redis for caching to improve performance

## Tech Stack

- Java 17
- Spring Boot
- H2 (primary database)
- Redis 6 (for caching)
- Docker and Docker Compose for containerization and orchestration
- Maven for dependency management and build automation

## Prerequisites

- Docker and Docker Compose installed on your system
- Java Development Kit (JDK) 17 or later (for local development)
- Maven (for local build and development)

## Getting Started

### Running with Docker Compose

1. Clone the repository:
   ```
   git clone `https://github.com/johnDevALX/url-shortener`
   cd url-shortener
   ```

2. Build and start the services:
   ```
   docker-compose up --build
   ```

3. The application will be available at `http://localhost:8080`

### API Endpoints

1. Shorten URL
    - Endpoint: `POST /v1/url/shorten`
    - Request Body:
      ```json
      {
        "fullUrl": "https://www.example.com/very/long/url/that/needs/shortening"
      }
      ```
    - Response: Short URL as a string

2. Redirect to Long URL
    - Endpoint: `GET /{shortCode}`
    - This will redirect to the original long URL

## Configuration

The application can be configured using environment variables in the Docker Compose file:

- `DATABASE_URL`: JDBC URL for the database
- `DATABASE_USERNAME`: Database username
- `DATABASE_PASSWORD`: Database password
- `DATABASE_DIALECT`: Hibernate dialect for the database
- `BASE_URL`: Base URL (application's base url e.g http://localhost:8080) for the shortened URLs
- `REDIS`: Redis connection URL

## Database Options

The service is configured to use PostgreSQL by default in production. However, it can be switched to MySQL if preferred. To use MySQL:

1. Comment out the PostgreSQL `db` service in `docker-compose.yml`
2. Uncomment the MySQL `db` service
3. Update the `DATABASE_URL` and `DATABASE_DIALECT` in the `app` service accordingly

## Development

For local development without Docker:

1. Ensure you have JDK 17+ and Maven installed
2. Set up a local H2 database and Redis instance
3. Update `application.properties` with your local database and Redis configurations
4. Run the application using Maven:
   ```
   mvn spring-boot:run
   ```

## Testing

To run the tests:

```
mvn test
```

## Deployment

While this README provides instructions for local deployment using Docker Compose, for a production environment, consider the following:

- Use a managed database service for better scalability and management
- Implement a reverse proxy or load balancer for improved security and performance
- Set up monitoring and logging solutions
- Implement proper backup and disaster recovery strategies

- For cloud deployment, reach via the below url
  - https://url-shortener-jlad.onrender.com

## Contributing

```
    email: ekenevics@gmail.com

```
