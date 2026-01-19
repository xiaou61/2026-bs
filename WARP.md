# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Repository Overview

This is a monorepo containing 41+ complete graduation design projects (毕业设计项目). Each project is numbered (001-041) and consists of separate backend and frontend folders.

## Project Structure

```
XXX-backend/      # Spring Boot backend
XXX-frontend/     # Vue/React frontend (when separate)
```

Some projects use integrated frontend (Thymeleaf templates in Spring Boot).

## Technology Stack

### Backend (Most Projects)
- Spring Boot 3.2.x
- MyBatis-Plus 3.5.5 (some use Spring Data JPA)
- MySQL 8.0 (some use PostgreSQL)
- JWT authentication (jjwt 0.11.5 or 0.12.x)
- Java 17+

### Frontend (Most Projects)
- Vue 3.4.x + Vite 5.x
- Element Plus 2.5.x
- Pinia state management
- Axios for HTTP
- Some projects use React + Ant Design or jQuery + Bootstrap

## Common Commands

### Backend (Spring Boot + Maven)
```bash
# Navigate to project backend directory first
cd XXX-backend

# Run with Maven wrapper (if available)
./mvnw spring-boot:run

# Or with Maven
mvn spring-boot:run

# Build JAR
mvn clean package -DskipTests

# Run tests
mvn test
```

### Frontend (Vue 3 + Vite)
```bash
# Navigate to project frontend directory first
cd XXX-frontend

# Install dependencies
npm install
# or
pnpm install

# Start dev server (usually runs on port 3000 or 5173)
npm run dev

# Build for production
npm run build
```

### Frontend (Thymeleaf - Integrated)
For projects with integrated frontend, just run the Spring Boot application - static files are served from `src/main/resources/static/` or `templates/`.

## Database Setup

Each project has its own database. SQL init scripts are typically located at:
- `XXX-backend/src/main/resources/sql/init.sql`

Database connection is configured in:
- `XXX-backend/src/main/resources/application.yml`

Default credentials are usually `root/1234` for local development.

## API & Authentication

- All APIs are prefixed with `/api/`
- JWT tokens are passed via `Authorization: Bearer <token>` header
- Default test accounts (vary by project):
  - Admin: `admin / admin123`
  - Teacher/Staff: `teacher001 / teacher123`
  - Student/User: `student001 / student123`

## Code Architecture

### Backend Structure
```
src/main/java/com/xiaou/
├── XxxApplication.java      # Main class
├── common/                  # Result, exceptions, handlers
├── config/                  # WebConfig, JwtInterceptor, MyBatisPlusConfig
├── controller/              # REST controllers
├── entity/                  # JPA/MyBatis entities
├── mapper/                  # MyBatis mapper interfaces
├── service/                 # Service interfaces
│   └── impl/                # Service implementations
└── utils/                   # JwtUtil, etc.
```

### Frontend Structure (Vue 3)
```
src/
├── api/            # Axios API modules
├── components/     # Reusable components
├── layout/         # Layout components
├── router/         # Vue Router config
├── stores/         # Pinia stores
├── utils/          # request.js (Axios wrapper)
└── views/          # Page components
```

## Working with Projects

1. **Identify the project number** (e.g., 001, 002) from the folder name
2. **Check project README** for specific tech stack details
3. **Backend port**: Usually 8080
4. **Frontend port**: Usually 3000 or 5173 (Vite default)
5. **API proxy**: Frontend dev servers proxy `/api` to backend

## Variations to Note

- Projects 030, 036 use **Spring Data JPA** instead of MyBatis-Plus
- Projects 030, 036 use **PostgreSQL** instead of MySQL
- Project 030 uses **React + Ant Design** frontend
- Project 031 uses **jOOQ** for database access and **htmx** for frontend
- Projects with Thymeleaf (008, 010, 012, 014, 015, 017, 023) are integrated frontend

## Configuration Patterns

### CORS Configuration
Configured in `WebConfig.java`, allows all origins for development.

### JWT Configuration
Secret key and expiration in `application.yml` or hardcoded in `JwtUtil.java`.

### MyBatis-Plus Features Used
- Pagination interceptor
- Logic delete (`deleted` field)
- Auto-fill for `createTime`, `updateTime`
- Camel case to underscore mapping
