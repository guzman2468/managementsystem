# PostgreSQL Docker Container Setup

This project uses **Docker** and **Docker Compose** to run a PostgreSQL database in a containerized environment.  
No local PostgreSQL installation is required.

---

##  Required Software

You must have the following installed on your machine:

### 1️⃣ Docker
Docker is required to run the PostgreSQL container.

#### macOS / Windows
Download and install **Docker Desktop**:
- https://www.docker.com/products/docker-desktop/


---

###  Verify Installation
After installation, run:
```bash
docker --version
docker compose version
```

### make your own application.properties file, follow the example and adjust the username and passowrd fields
spring.application.name=managementsystem
spring.datasource.url=jdbc:postgresql://localhost:5321/libmanagedb
spring.datasource.username=yourUsernameHere
spring.datasource.password=yourPasswordHere
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect



### Make similar edits to docker-compose.yaml
in the POSTGRES_USER and POSTGRES_PASSWORD fields, but the same username and password you used for the application.properties file.
Please ensure the 5321 port on your machine is open as the container listenes on this port to connect to the PostgreSQL DB.

### Connecting to your DB from command line
navigate to the root of the project from the command line and type: docker exec -it postgres-library-management psql -U <yourUsernameHere>
.
You should not be prompted for a password but if you are, input the password you used in application.properties

From here use \l to list all DB's and: \c libmanagedb
to connect to the DB for this project. From here all your standard SQL commands will work.