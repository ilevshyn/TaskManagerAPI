# TaskManagerAPI
## 1. Overview
REST API to manage and store tasks.
Supports basic username/password authorization via JWT token.
Uses Postgres as database. API design is available in http://localhost:8080/swagger-ui.html.

## 2. Prerequisites
- JDK 24
- Docker
 
## 3. Getting started
1. `git clone https://github.com/ilevshyn/TaskManagerAPI.git`
2. `cd .\app\`
3. `./mvnw clean package -DskipTests`
4. `cd ..`
5. `docker-compose up --build`

For the api use port 8080, Postgres will be available on port 5432, and adminer on 6767.

## 4. API usage
Everything except `/auth/register` and `/auth/login` and swagger is available with bearer token.
Bearer token can be received after registering user and logging in. 