# RESTAPISpringBoot 

# 1. Create and Switch to a New Branch:
Before you start working on the new project, create a new branch for it:
git checkout -b feature/new_project_name
and checkout to that branch 

# 2.Start Working on the Project:

Once you’re on the new branch, you can start working on your new project. Make changes and add files as needed.

# 3.Stage and Commit Changes:
git add .
git commit -m "Add initial implementation for new_project_name"
git push -u origin feature/new_project_name

4.To confirm that the branch has been pushed and is available remotely, list all remote branches:
git branch -r


# ---------PostgreSQL Setup with Docker
Prerequisites
Docker installed on your machine.
# 1. Create a docker-compose.yml File
Create a docker-compose.yml file in your project directory with the following content:
version: '3.8'

services:
  postgres:
    image: postgres:13
    container_name: postgres-db
    environment:
      POSTGRES_DB: bookdb
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: admin123
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data

volumes:
  pgdata:


# This configuration will:

Pull the postgres:13 image if it's not already on your system.
Create a PostgreSQL container named postgres-db.
Expose the PostgreSQL service on port 5432.
Persist database data using Docker volumes.
#  2. Start the PostgreSQL Container
docker-compose up -d
# 3. Verify the PostgreSQL Container is Running
docker ps
# Access PostgreSQL CLI (Optional)

# 5. Connecting Your Application to PostgreSQL
In your application (e.g., a Spring Boot application), configure the connection to PostgreSQL with the following properties in your application.properties or application.yml file:
spring.datasource.url=jdbc:postgresql://localhost:5432/bookdb
spring.datasource.username=admin
spring.datasource.password=admin123
spring.datasource.driver-class-name=org.postgresql.Driver

# 6. Stopping the PostgreSQL Container
docker-compose down
