# 👥 Employee Management System

Full-stack RBAC Employee Management System built with **Java Spring Boot + Angular**.

## 🚀 Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 17, Spring Boot 3.2, Spring Security |
| Auth | JWT (JSON Web Tokens) |
| Database | MySQL + JPA/Hibernate |
| API Docs | Swagger / OpenAPI 3 |
| Frontend | Angular 17 |
| Build | Maven |

## 🔐 RBAC — Role Based Access Control

| Action | ADMIN | MANAGER | EMPLOYEE |
|--------|-------|---------|----------|
| View employees | ✅ | ✅ | ✅ |
| Add employee | ✅ | ✅ | ❌ |
| Edit employee | ✅ | ✅ | ❌ |
| Delete employee | ✅ | ❌ | ❌ |

## 📁 Project Structure

```
employee-management/
├── backend/                    ← Spring Boot
│   ├── src/main/java/com/pooja/ems/
│   │   ├── controller/         ← REST Controllers
│   │   ├── service/            ← Business Logic
│   │   ├── repository/         ← JPA Repositories
│   │   ├── model/              ← JPA Entities
│   │   ├── dto/                ← Request/Response DTOs
│   │   ├── security/           ← JWT + Spring Security Config
│   │   └── exception/          ← Global Exception Handler
│   └── pom.xml
└── frontend/                   ← Angular
    └── src/app/
        ├── components/         ← Login, Dashboard, EmployeeList, Form, Navbar
        ├── services/           ← AuthService, EmployeeService
        ├── guards/             ← AuthGuard
        ├── interceptors/       ← JwtInterceptor
        └── models/             ← TypeScript interfaces
```

## ▶️ Running Locally

### Backend
```bash
# 1. MySQL mein database banao
CREATE DATABASE employee_db;

# 2. application.properties mein credentials set karo
# spring.datasource.username=root
# spring.datasource.password=yourpassword

# 3. Run karo
cd backend
mvn spring-boot:run
# Runs on http://localhost:8081
```

### Frontend
```bash
cd frontend
npm install
ng serve
# Runs on http://localhost:4200
```

## 🔌 API Endpoints

| Method | Endpoint | Auth | Role |
|--------|----------|------|------|
| POST | /api/auth/login | None | Public |
| POST | /api/auth/register | None | Public |
| GET | /api/employees | JWT | All |
| POST | /api/employees | JWT | Admin/Manager |
| PUT | /api/employees/{id} | JWT | Admin/Manager |
| DELETE | /api/employees/{id} | JWT | Admin only |
| GET | /api/employees/search?name= | JWT | All |
| GET | /api/employees/department/{dept} | JWT | All |

## 📖 Swagger UI

```
http://localhost:8081/swagger-ui.html
```

## 👤 Demo Credentials

| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ADMIN |
| manager | manager123 | MANAGER |
| employee | emp123 | EMPLOYEE |

## 👩‍💻 Built By

**Pooja Dixit** — Java Spring Boot Developer @ TCS
