# swiftcart-api-automation

REST Assured API automation framework for validating the Swiftcart backend API.

## Tech Stack

- Java 17
- REST Assured 5.4.0
- TestNG 7.10.2
- Jackson 2.17.0
- Maven

## Project Structure

```text
swiftcart-api-automation/
├── src/test/java/
│   ├── base/BaseTest.java          # Common setup for all tests
│   ├── config/ConfigManager.java   # Loads config.properties
│   ├── constants/Endpoints.java    # API endpoint paths
│   ├── constants/StatusCodes.java  # HTTP status code constants
│   ├── models/LoginRequest.java    # Request body model for login
│   └── tests/
│       ├── HealthTests.java        # GET /health — API availability check
│       └── AuthTests.java          # POST /auth/login — valid and invalid login
├── src/test/resources/
│   ├── config.properties           # Base URI and credentials
│   └── testng.xml                  # TestNG suite configuration
└── pom.xml
```

## Installation

```bash
mvn clean install -DskipTests
```

## Run Tests

```bash
mvn clean test
```

## Test Coverage

| Test Class | Endpoint | Scenarios |
|---|---|---|
| HealthTests | GET /health | API returns 200 with status ok |
| AuthTests | POST /auth/login | Valid login returns token |
| AuthTests | POST /auth/login | Wrong password returns 401 |
| AuthTests | POST /auth/login | Unknown email returns 401 |

## Target API

Tests run against the **Swiftcart** demo API — a portfolio e-commerce application with a real Supabase edge function backend.

- Frontend: https://swiftcart-shop-app.lovable.app
- API base: `https://djxdhlwkccbdjucfggrn.supabase.co/functions/v1/api`
