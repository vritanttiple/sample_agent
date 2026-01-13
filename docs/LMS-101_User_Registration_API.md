# LMS-101 User Registration API Documentation

## Overview
Implements user registration for the Library Management System. New users can register via `/api/auth/register`.

## Endpoint
- **URL:** `/api/auth/register`
- **Method:** POST
- **Content-Type:** application/json

## Request Body
```
{
  "email": "user@example.com",
  "password": "yourSecurePassword",
  "name": "John Doe"
}
```

## Validation Rules
- **Email:** Must be valid format, not previously registered
- **Password:** Minimum 8 characters
- **Name:** Required

## Success Response
- **Code:** 201 CREATED
- **Body:**
```
{
  "message": "Registration successful",
  "userId": 1
}
```

## Error Responses
- **Duplicate Email:**
```
{
  "error": "Email is already registered"
}
```
- **Invalid Input:**
```
{
  "error": "Invalid email format"
}
```

## Database Table (users)
| Field      | Type         | Constraints                 |
|------------|--------------|-----------------------------|
| id         | BIGINT       | PK, AUTO_INCREMENT          |
| email      | VARCHAR(100) | UNIQUE, NOT NULL            |
| password   | VARCHAR(255) | NOT NULL                    |
| name       | VARCHAR(50)  | NOT NULL                    |
| created_at | DATETIME     | NOT NULL, DEFAULT NOW()     |

## Setup Instructions
1. Ensure MySQL is running and accessible.
2. Set `spring.datasource.username` and `spring.datasource.password` in `application.properties`.
3. Run the application with Java 17 and Spring Boot.

## Maintenance Procedures
- Monitor registration logs for errors.
- Periodically review password hashing algorithm.

## Troubleshooting
- **Error:** "Email is already registered"
  - Solution: Use a unique email address.
- **Error:** Database connection failure
  - Solution: Check MySQL credentials and network.

## Future Recommendations
- Add email verification.
- Extend registration fields as needed.
