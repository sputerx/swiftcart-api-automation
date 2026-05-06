# swiftcart-api-automation

REST Assured + TestNG API automation for the **Swiftcart** demo e-commerce backend (Supabase edge functions). Validates health, auth, catalog, cart, and checkout against a live base URL.

## Tech stack

- Java 17
- Maven
- REST Assured 5.4.0
- TestNG 7.10.2
- Jackson 2.17.0 (`jackson-databind`)

## Prerequisites

- **JDK 17** (or newer JDK with `--release 17` as in `pom.xml`)
- **Maven 3.x** on your `PATH` (`mvn -version`)

## Project layout

```text
swiftcart-api-automation/
├── pom.xml
├── src/test/java/
│   ├── base/BaseTest.java           # RestAssured defaults, JSON content-type, failure logging
│   ├── config/ConfigManager.java    # Loads src/test/resources/config.properties
│   ├── constants/Endpoints.java     # Path constants (e.g. /health, /products/{id})
│   ├── constants/StatusCodes.java   # HTTP status constants
│   ├── models/LoginRequest.java     # Login request body (email, password)
│   └── tests/
│       ├── HealthTests.java         # GET /health
│       ├── AuthTests.java           # POST /auth/login
│       ├── ProductsTests.java       # GET /products, GET /products/{id}
│       ├── CartTests.java           # GET /cart, POST /cart/items, DELETE /cart/items/{productId}
│       └── CheckoutTests.java       # POST /checkout (happy path + validation)
└── src/test/resources/
    ├── config.properties            # base.uri + demo credentials
    └── testng.xml                   # TestNG suite (all classes above)
```

## Configuration

Edit `src/test/resources/config.properties`:

- **`base.uri`** — API root (no trailing slash). Default targets the Swiftcart Supabase function URL.
- **`auth.email`** / **`auth.password`** — used by login tests.

Example:

```properties
base.uri=https://YOUR-PROJECT.supabase.co/functions/v1/api
auth.email=demo@swiftcart.com
auth.password=password123
```

## Install & run

From the project root:

```bash
mvn clean test
```

Expect **`BUILD SUCCESS`** and **`Tests run: 15, Failures: 0`**.

Compile only (no HTTP calls):

```bash
mvn clean compile test-compile
```

## Test coverage (15 tests)

| Class | Focus |
| --- | --- |
| **HealthTests** | `GET /health` — service up, expected JSON shape |
| **AuthTests** | `POST /auth/login` — valid token; wrong password; unknown email |
| **ProductsTests** | `GET /products` — list wrapped in `products`; first item has `id`, `name`, `price`; `GET /products/{id}` — 200 for real id, 404 for bogus id |
| **CartTests** | `GET /cart`; add item with real `productId` + `quantity`; bad body returns 400; delete by `productId` (tolerates 200/204/404 depending on server state) |
| **CheckoutTests** | `POST /checkout` — success expects **201** when payload includes **`firstName`** and **`lastName`** (not a single `name` field); missing fields / empty body → **400** |

## API notes (why assertions look like this)

- **`GET /products`** returns an object with a **`products`** array, not a bare JSON array. Paths in tests use `products[0].…`.
- **Checkout** validates separate **`firstName`** / **`lastName`** fields; sending only `name` returns **400** with validation errors.

## Target app

- Demo storefront (UI): [swiftcart-shop-app.lovable.app](https://swiftcart-shop-app.lovable.app)

This suite is a **portfolio / learning** project: it hits a public demo API. Do not commit secrets; keep demo credentials appropriate for a public repo.

## Troubleshooting

- **Maven/Java warnings** (`Unsafe`, `restricted method`): common on newer JDKs with older Maven/Guava; they do not mean tests failed if you still see **BUILD SUCCESS**.
- **SLF4J “Failed to load StaticLoggerBinder”**: REST Assured runs without a binding; optional to fix by adding `slf4j-simple` later.
