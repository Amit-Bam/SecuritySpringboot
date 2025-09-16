# Spring Security Role-Based Authentication

This application demonstrates role-based authentication and authorization using Spring Security.

## Default User Credentials

The application is pre-configured with the following default accounts:

### Admin User
- **Username:** admin
- **Password:** admin123
- **Role:** ROLE_ADMIN

### Regular User
- **Username:** user
- **Password:** user123
- **Role:** ROLE_USER

## Security Features

- HTTP Basic Authentication
- Role-based access control
- Encrypted password storage with BCrypt
- Stateless session management

## URL Access Control

- `/api/admin/**` - Accessible only to users with ROLE_ADMIN
- `/api/user/**` - Accessible to users with ROLE_USER and ROLE_ADMIN
- `/`, `/login`, `/api/public/**` - Publicly accessible

## Getting Started

1. Run the application using Spring Boot
2. Access the endpoints using the appropriate credentials
3. Admin can access all endpoints
4. Regular users can only access user endpoints
