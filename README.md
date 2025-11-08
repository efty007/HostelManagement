# HostelManagement (JSP/Servlets, Tomcat, MySQL)

A complete Hostel Management web application scaffolded with JSP/Servlets, Tomcat, and MySQL.

## Requirements
- Java 8+ (JDK)
- Maven 3.6+
- Apache Tomcat 9.x (localhost:8080)
- Docker (optional, to run MySQL)

## Quick Start

### 1) Start MySQL (Docker)
1. Copy env file:
   - Windows PowerShell: `Copy-Item .env.example .env`
2. Edit `.env` and set `MYSQL_ROOT_PASSWORD`.
3. Start DB:
   - `docker compose up -d`
4. The DB will initialize automatically with schema and seed data (admin + rooms).

If using local MySQL instead, create a database `hostel_db`, then run the SQL in `sql/init` manually, and set env vars (host/user/password) for the app or edit DB credentials in code.

### 2) Build the WAR
```
mvn clean package
```
The WAR is generated at `target/HostelManagement.war` (contains WEB-INF/lib with MySQL driver).

### 3) Deploy to Tomcat
- Copy `target/HostelManagement.war` to Tomcat's `webapps/` folder.
- Start Tomcat (or it auto-deploys if already running).
- Access:
  - Home: http://localhost:8080/HostelManagement/
  - Admin Login: http://localhost:8080/HostelManagement/adminLogin.jsp
  - Student Login: http://localhost:8080/HostelManagement/studentLogin.jsp

### Admin Credentials (seeded)
- Email: `admin@hostel.com`
- Password: `admin123`

## Project Structure
```
HostelManagement/
  src/main/java/com/hostel/{model,dao,servlet,util}
  src/main/webapp/{WEB-INF,css,js,images}
  sql/init/*.sql
  docker/mysql/init/*.sql
```

## Configuration
The app reads DB connection from environment variables if present:
- `MYSQL_HOST` (default: `localhost`)
- `MYSQL_PORT` (default: `3306`)
- `MYSQL_DATABASE` (default: `hostel_db`)
- `MYSQL_USER` (default: `root`)
- `MYSQL_PASSWORD` (default: empty)

You may also hardcode or adjust credentials in `DBConnection.java` for local testing.

## Notes
- Ensure Tomcat 9.x (javax servlet API). For Tomcat 10 (jakarta), migrations are needed.
- If `git` is not available on your PATH, install Git to initialize the repository.
