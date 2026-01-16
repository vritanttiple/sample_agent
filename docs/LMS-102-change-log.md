# Change Log: LMS-102 - Implement User Login with JWT

## Files Added/Modified
- src/main/java/com/example/lms/model/User.java: User entity for authentication
- src/main/java/com/example/lms/repository/UserRepository.java: User data access
- src/main/java/com/example/lms/service/AuthService.java: Authentication logic and JWT issuance
- src/main/java/com/example/lms/security/JWTProvider.java: JWT token generator
- src/main/java/com/example/lms/controller/AuthController.java: Login endpoint
- src/main/java/com/example/lms/config/SecurityConfig.java: Security configuration
- docs/LMS-102-authentication-feature.md: Feature documentation and setup guide

## Summary
- Implemented login functionality using layered architecture
- Used Spring Security for password encoding and endpoint protection
- Generates JWT on successful login, returns 401 on failure
- Provided documentation for usage, setup, and troubleshooting
