
# NovaraSpace Backend

Spring Boot backend API for https://novaraspace.com

[Frontend Repository](https://github.com/2outblue/novara-fe)

NovaraSpace is a personal project built around an airline-like booking platform set in a fictional setting.

This repository contains the API, authentication system, business logic and database integration that power the NovaraSpace application.

All public demo accounts with the credentials can be seen here: https://novaraspace.com/information/section/Account


## Features

### Authentication & Authorization
- JWT-based authentication with refresh tokens (custom implementation)
- Email verification
- Password change and reset functionality
- Role-based access control
- Public demo accounts with restricted permissions

### Flight & Booking Management
- Flight search and flight availability search
- Auto flight generation and cleanup
- Balanced flight padding range returns
- Booking creation and management
- Booking quote system for validation of passengers, flights and prices
- Seat/cabin availability handling
- Payment tracking

### Other features
- Account management
- Audit logging
- Rate limiting
- Standard response times on sensitive endpoints
- Response caching on selected endpoints
- Scheduled cleanup jobs
- Email delivery service

## Deployment

The production system is hosted on a Linux VPS.

Deployment is automated through GitHub Actions.

Changes pushed to the main branch trigger a deployment workflow that builds and deploys the application to the production VPS. Nginx is used to route /api requests to the docker backend container.

The frontend build is hosted on the same VPS environment and documented in the [frontend repository](https://github.com/2outblue/novara-fe).

## Technology Stack

- Java
- Spring Boot
- MySQL
- Flyway
- Gradle
- Docker

## Local Setup

### Prerequisites

- Docker
- Docker Compose

### Run

```bash
docker compose -f compose.local.yaml up --build
```

