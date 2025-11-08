# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Commands

- Database (Docker):
  - PowerShell
    ```powershell
    Copy-Item .env.example .env
    notepad .env   # set MYSQL_ROOT_PASSWORD (and optionally MYSQL_* connection vars)
    docker compose up -d
    ```
  - Useful DB ops
    ```powershell
    docker compose ps
    docker compose logs -f
    docker compose down
    ```

- Build WAR:
  ```bash
  mvn clean package
  ```
  - Output: target/HostelManagement.war

- Tests:
  ```bash
  mvn -q test
  ```
  - Single class:
    ```bash
    mvn -q -Dtest=ClassNameTest test
    ```
  - Single method:
    ```bash
    mvn -q -Dtest=ClassNameTest#methodName test
    ```
  - Note: No tests are present under src/test/java at the moment; commands above are for when tests are added.

- Deploy to local Tomcat 9.x (Windows PowerShell):
  ```powershell
  $Env:TOMCAT_HOME = 'C:\path\to\apache-tomcat-9.x'
  Copy-Item target\HostelManagement.war "$Env:TOMCAT_HOME\webapps\"
  & "$Env:TOMCAT_HOME\bin\startup.bat"
  ```
  - Access (active impl):
    - Home: http://localhost:8080/HostelManagement/
    - Admin: http://localhost:8080/HostelManagement/admin/login
    - Student: http://localhost:8080/HostelManagement/student/login

- App DB configuration (choose one based on code path in use):
  - Env vars (used by com.hostel.util.DBConnection):
    ```powershell
    $Env:MYSQL_HOST='localhost'
    $Env:MYSQL_PORT='3306'
    $Env:MYSQL_DATABASE='hostel_db'
    $Env:MYSQL_USER='root'
    $Env:MYSQL_PASSWORD='...'
    ```
  - Properties file (used by com.hostelmgmt.util.DBConnection): edit src/main/resources/db.properties

## High-level architecture

- Active implementation: com.hostelmgmt.*
  - Controllers forward to JSPs under WEB-INF/views and load DB settings from classpath src/main/resources/db.properties.
  - Legacy com.hostel.* is disabled at build time to avoid duplicate servlet mappings.

- Build/packaging
  - Maven WAR (Servlet 4, Java 8). Dependencies: javax.servlet-api (scope provided), mysql-connector-java, jstl.
  - Maven exclusions in pom.xml:
    - maven-compiler-plugin excludes com/hostel/** from compilation.
    - maven-war-plugin excludes legacy root JSPs: adminLogin.jsp, adminPanel.jsp, studentLogin.jsp, studentPanel.jsp.

- Views and static assets
  - Primary JSPs: src/main/webapp/WEB-INF/views/*.jsp (not directly accessible).
  - Static assets under src/main/webapp/js and src/main/webapp/assets/js.

- Database/config
  - Edit src/main/resources/db.properties to configure JDBC URL, username, and password.

- Important from README
  - Seeded admin: admin@hostel.com / admin123.
  - Primary URLs under the HostelManagement context path (e.g., /admin/login, /student/login).
  - Target Tomcat 9.x (javax.*); Tomcat 10+ requires jakarta.* migration.

## Repository pointers

- README.md: quick start, required tooling, access URLs, seeded credentials.
- .env.example: sample env for Docker and optional app DB.
- pom.xml: Maven configuration and dependencies.
- docker-compose.yml: local MySQL 8 service.
