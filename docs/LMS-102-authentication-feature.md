# LMS-102: Implement User Login with JWT

## Overview
Implements login functionality for registered users. On successful authentication, returns a signed JWT token.

## Architecture
- Layered: Controller → Service → Repository
- Technologies: Java 17, Spring Boot, Spring Security, JWT

## Endpoints
- `POST /api/auth/login`
  - Request Body: `{ "username": "<username>", "password": "<password>" }`
  - Success Response: `{ "token": "<jwt-token>" }`
  - Failure Response: `401 Unauthorized` with "Invalid credentials"

## Key Classes
- `User`: JPA entity for user data
- `UserRepository`: Data access for users
- `AuthService`: Handles authentication and JWT creation
- `JWTProvider`: Creates JWT tokens
- `AuthController`: Exposes login endpoint
- `SecurityConfig`: Configures password encoding and endpoint security

## Setup
1. Ensure DB contains users (username, hashed password).
2. Update application dependencies for Spring Boot, Security, JWT.
3. Configure secret key in `JWTProvider`.
4. Run application and POST to `/api/auth/login` for authentication.

## Maintenance
- Rotate JWT secret regularly.
- Monitor login attempts and logs.
- Update dependencies for security patches.

## Troubleshooting
- "Invalid credentials": Check username/password and DB data.
- JWT errors: Verify secret key configuration.
- Endpoint not reachable: Check SecurityConfig and server logs.
