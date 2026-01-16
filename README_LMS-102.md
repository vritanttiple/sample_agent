# LMS-102: Implement User Login with JWT

## Overview
This feature enables registered users to log in with their credentials and receive a signed JWT token for authenticated access, using Java 17, Spring Boot, and Spring Security.

## Acceptance Criteria
- Valid credentials return a signed JWT
- Invalid credentials return an authentication error

## Architecture
- Layered structure: Controller → Service → Repository
- Dependencies: UserRepository, PasswordEncoder, JWTProvider

## Files Modified/Created
- controller/AuthController.java
- service/AuthService.java
- repository/UserRepository.java
- security/JWTProvider.java
- configuration/SecurityConfig.java
- application.yml

## Usage
POST /api/auth/login with username and password

## Maintainer: Senior Backend Engineer
