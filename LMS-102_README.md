# LMS-102: Implement User Login API

## Overview
Implements user login functionality with JWT authentication and BCrypt password hashing, following clean layered architecture.

## API Endpoint
- **POST /api/auth/login**
  - **Request:**
    - JSON: `{ "username": "<username>", "password": "<password>" }`
  - **Success Response:**
    - 200 OK: `{ "success": true, "message": "Login successful", "token": "<jwt>" }`
  - **Failure Response:**
    - 401 Unauthorized: `{ "success": false, "message": "Invalid credentials", "token": null }`

## Layered Architecture
- Controller: `AuthController.java`
- Service: `AuthService.java`, `UserService.java`
- Repository: `UserRepository.java`
- Model: `User.java`, `LoginRequest.java`, `LoginResponse.java`
- Security: `JwtProvider.java`

## Password Hashing
- Passwords are hashed using BCrypt on registration via `UserService.createUser()`.

## JWT Token Generation
- On successful login, a JWT token is generated using `JwtProvider`.

## Setup Instructions
1. Ensure Java 17 and Spring Boot dependencies are present (see `pom.xml`).
2. Add `io.jsonwebtoken:jjwt` dependency for JWT support.
3. Configure datasource in `application.properties` for User entity persistence.
4. Build and run the application.

## Change Log
- Added layered login API: controller, service, repository
- Integrated BCrypt for password hashing
- Integrated JWT for authentication

## Usage
- Register a user via `UserService.createUser()` (ensure password is hashed)
- Login via `/api/auth/login` to receive JWT token

## Compliance & Audit
- Fulfills LMS-102 story and sub-tasks
- All changes are tracked in branch `LMS-102`
- Follows Spring Security best practices

## Future Recommendations
- Extend JWT validation for protected endpoints
- Add refresh tokens and logout support
- Consider OAuth2 integration
