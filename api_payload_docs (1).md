# Mobile Mart Authentication API Documentation

This document defines the REST API endpoints, request payloads, and response payloads for the Mobile Mart E-commerce backend authentication system.

Base URL: `/api/auth`

## 1. Register a New User
**Endpoint**: `POST /register`
**Protected**: No

**Request Payload**:
```json
{
  "fullName": "John Doe",
  "email": "john@example.com",
  "mobileNumber": "1234567890",
  "password": "Password123!",
  "confirmPassword": "Password123!"
}
```

**Response Payload (Success - 200 OK)**:
```json
{
  "success": true,
  "message": "User registered successfully",
  "data": null
}
```

## 2. Login
**Endpoint**: `POST /login`
**Protected**: No

**Request Payload**:
```json
{
  "identifier": "john@example.com", 
  "password": "Password123!"
}
```
*(Note: `identifier` can be either the email address or the mobile number).*

**Response Payload (Success - 200 OK)**:
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIi...",
    "userId": 1,
    "fullName": "John Doe",
    "email": "john@example.com",
    "mobileNumber": "1234567890"
  }
}
```

## 3. Logout
**Endpoint**: `POST /logout`
**Protected**: Yes (Requires `Authorization: Bearer <token>` header)

**Request Payload**: None

**Response Payload (Success - 200 OK)**:
```json
{
  "success": true,
  "message": "Logged out successfully",
  "data": null
}
```

## 4. Forgot Password (Generate OTP)
**Endpoint**: `POST /forgot-password`
**Protected**: No

**Request Payload**:
```json
{
  "identifier": "john@example.com"
}
```

**Response Payload (Success - 200 OK)**:
```json
{
  "success": true,
  "message": "OTP generated and sent successfully",
  "data": null
}
```

## 5. Verify OTP
**Endpoint**: `POST /verify-otp`
**Protected**: No

**Request Payload**:
```json
{
  "identifier": "john@example.com",
  "otp": "123456"
}
```

**Response Payload (Success - 200 OK)**:
```json
{
  "success": true,
  "message": "OTP verified successfully",
  "data": "a3b2c1-d4e5-f6g7-h8i9-j0k1l2" // This is the reset token to use in the next step
}
```

## 6. Reset Password
**Endpoint**: `POST /reset-password`
**Protected**: No

**Request Payload**:
```json
{
  "identifier": "john@example.com",
  "otpToken": "a3b2c1-d4e5-f6g7-h8i9-j0k1l2",
  "newPassword": "NewPassword123@",
  "confirmPassword": "NewPassword123@"
}
```

**Response Payload (Success - 200 OK)**:
```json
{
  "success": true,
  "message": "Password reset successfully",
  "data": null
}
```

## 7. Change Password
**Endpoint**: `PUT /change-password`
**Protected**: Yes (Requires `Authorization: Bearer <token>` header)

**Request Payload**:
```json
{
  "currentPassword": "Password123!",
  "newPassword": "NewPassword123@",
  "confirmPassword": "NewPassword123@"
}
```

**Response Payload (Success - 200 OK)**:
```json
{
  "success": true,
  "message": "Password changed successfully",
  "data": null
}
```

## Validation Errors (400 Bad Request)
If any input validation fails (e.g., password too weak, missing field), the API will return:
```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "password": "Password must contain uppercase, lowercase, number, and special character",
    "mobileNumber": "Mobile number must contain exactly 10 digits"
  }
}
```
