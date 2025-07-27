# 🏦 common-core-lib — Shared Core Library for Banking Microservices

A high-quality, production-ready Java library providing cross-cutting concerns used across all microservices in a distributed banking system. This module is designed with clean architecture, testability, security, localization, and observability in mind.

---

## 📦 Purpose

The `common-core-lib` centralizes logic that is reused across services, such as:

- ✅ Global error handling
- ✅ Internationalized error messages
- ✅ Request-scoped context propagation
- ✅ Base audit entity structure
- ✅ Structured exception system
- ✅ Thread-safe traceable metadata

It enforces standardization and reduces boilerplate across the entire banking platform.

---

## 📁 Module Structure

```
common-core-lib/
├── config/                    # Spring configuration (e.g. i18n message source)
├── context/                   # Thread-local RequestContext & filter
├── domain/                    # Shared domain model like BaseEntity
├── exception/                 # ApiException, ErrorCode, ExceptionHandler
├── utils/                     # Utility classes (UUID parsing, header utils)
└── resources/
    └── messages.properties    # i18n error messages (EN, VI, ZH)
```

---

## 🔐 Features

### ✅ Request Context Propagation

Supports user/session/correlation tracing via `ThreadLocal`:

- `X-User-Id`
- `X-Roles`
- `X-Correlation-ID`
- `X-Request-ID`
- `X-Username`, `X-Email`

```java
RequestContext ctx = RequestContextHolder.getContext();
UUID userId = ctx.getUserId();
String ip = ctx.getIpAddress();
```

### ✅ Exception Handling (API-safe)

Standard exception flow across all services:

```java
throw new ApiException(ErrorCode.ACCOUNT_NOT_FOUND);
```

Global handler maps this into:

```json
{
  "code": "ACCOUNT_NOT_FOUND",
  "message": "Account not found",
  "timestamp": "2025-07-27T12:45:30Z",
  "path": "/v1/accounts/lookup",
  "correlationId": "e219..."
}
```

### 🌍 Multi-Language Error Messages

Supports automatic error translation based on locale:

- `messages.properties` (default: English)
- `messages_vi.properties`
- `messages_zh.properties`

Spring will resolve the appropriate message based on the `Accept-Language` header.

---

## 🧩 Integration Example (Other Services)

1. Add dependency in your microservice:

```xml
<dependency>
  <groupId>com.banking</groupId>
  <artifactId>common-core-lib</artifactId>
</dependency>
```

2. Use `RequestContextHolder` to access user context

3. Throw `ApiException` with an `ErrorCode`

4. Handle uniformly with `GlobalExceptionHandler`

---

## 📌 Error Code Standard

Over 50+ well-defined `ErrorCode` values organized by domain:

- Gateway: `GATEWAY_UNAUTHORIZED`, `GATEWAY_RATE_LIMIT_EXCEEDED`
- Auth: `AUTH_USER_NOT_FOUND`, `AUTH_ACCOUNT_LOCKED`
- Transaction: `TX_LIMIT_EXCEEDED`, `TX_CURRENCY_MISMATCH`
- Validation: `VALIDATION_ERROR`, `MISSING_REQUIRED_FIELD`
- Business rules: `OPERATION_NOT_ALLOWED`, `DUPLICATE_REQUEST`

See [`ErrorCode.java`](./src/main/java/com/banking/common/exception/ErrorCode.java)

---

## 🛡️ Compliance Ready

- UTC timestamps
- ISO 8601 formatting
- Safe error exposure
- Traceable logs via correlation IDs
- Localization compliant

---

## 🛠 Tech Stack

- Java 17+
- Spring Boot 3.5.x
- Maven BOM (`banking-bom`)
- Jakarta Servlet 6.x
- ThreadLocal
- SLF4J
- Lombok (for `@Builder`, `@Getter`, etc.)

---

## 🔄 Roadmap (Optional Enhancements)

- [ ] Kafka audit event support
- [ ] TraceId propagation to logs via MDC
- [ ] Context propagation in async (e.g. `@Async`)
- [ ] `BankingDateUtils` for financial date operations

---

## 👥 Maintainers

- Lead Architect: `nguyenducbaokey@gmail.com`
- Version: `1.0.0`

---

## 📜 License

Proprietary – Internal use only. © 2025.