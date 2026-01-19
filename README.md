# Slotify backend

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![License](https://img.shields.io/github/license/Jaros-777/slotify-backend?style=for-the-badge)

**Slotify Backend** is a robust server-side application built with the Spring Boot framework. It serves as the core engine for a slot/booking management system, providing a stable RESTful API and seamless PostgreSQL database integration.

---

## ✨ Features
- 🏗️ **RESTful API:** Clean and predictable endpoints for frontend communication.
- 🗄️ **PostgreSQL Integration:** Utilizes Spring Data JPA for efficient data persistence and management.
- 🐳 **Dockerized:** Includes a `Dockerfile` for easy containerization and deployment.
- ⚙️ **Environment Configuration:** Supports externalized configuration via `.env` or system variables for secure credential management.
- 🛠️ **Scalable Architecture:** Designed with modern Java best practices in mind.

---


## 🛠 Tech Stack
* **Language:** Java 20+
* **Framework:** Spring Boot 3.x
* **Database (local):** PostgreSQL, Supabase (only for storage profiles and services pictures)
* **Database (prod):** Supabase
* **Build Tool:** Maven
* **Containerization:** Docker

---

## 🚀 Getting Started  
This section explains how to run the Slotify Backend project locally.  

### ✅ Prerequisites  
- Java 17 or later  
- Maven (or use your IDE’s built-in Maven support)  
- PostgreSQL (recommended for production)  
- (optional) H2 Database (used for testing or local development)  
- Git (optional, for cloning the repository)

---

## 🚀 Getting Started

### ✅ Prerequisites
- Java 20
- Maven installed
- PostgreSQL (local instance or Docker container)

### 1️⃣ Database Setup
Log in to your PostgreSQL console and execute:
```sql
CREATE DATABASE slotify;
CREATE USER slotify_user WITH ENCRYPTED PASSWORD 'your_secure_password';
GRANT ALL PRIVILEGES ON DATABASE slotify TO slotify_user;
```
### 2️⃣ Installation
Clone the repository to your local machine:
```sql
git clone [https://github.com/Jaros-777/slotify-backend.git](https://github.com/Jaros-777/slotify-backend.git)
cd slotify-backend
```
### 3️⃣ Configuration
Update your database credentials in ```src/main/resources/application-local.properties```:
```sql
  datasource:
    url: jdbc:postgresql://localhost:5432/${LOCAL_DB_NAME}
    username: ${LOCAL_DB_USERNAME}
    password: ${LOCAL_DB_PASSWORD}
```
For supabase storage and JWT secret key (64 byte - for example ```verylongsecretkeythatis32charsverylongsecretkeythatis32chars``` )
in ```src/main/resources/application.properties```:
```sql
supabase_url=${SUPABASE_BUCKET_URL}
supabase_key=${SUPABASE_BUCKET_KEY}
supabase_bucket=${SUPABASE_BUCKET_NAME}

jwt_secret_key=${JWT_SECRET_KEY}
```
⚙️ Setting up Spring Boot Profile (Local) :  
1. To run the backend locally with the local profile, follow these steps in IntelliJ:  
2. Go to the Run menu and select Edit Configurations…  
3. Select your run configuration, e.g., SlotifyBackendApplication  
4. Find the Environment Variables section  
5. Click Add (➕) and set:  
    Name: ```SPRING_PROFILES_ACTIVE```   
    Value: ```local```   
6. Apply the changes and run the application
   
This ensures Spring Boot uses the local profile when running from IntelliJ.

### 4️⃣ Running the Application
Using Maven:
```sql
mvn spring-boot:run
```
The server will start at: http://localhost:8080

---

### 🐳 Running with Docker
You can easily build and run the application (backend and PostgreSQL database) using the provided Docker Compose:

⚙️ Environment variables:  
Before running, you can optionally set the following environment variables in docker-compose.yml: 

```DOCKER_DB_NAME``` – database name (default: postgres)  
```DOCKER_DB_USERNAME``` – database user (default: postgres)  
```DOCKER_DB_PASSWORD``` – database password (default: postgres)  

These are not strictly required, but for the application to work correctly, it’s recommended to provide real passwords. 
```
SUPABASE_BUCKET_URL
SUPABASE_BUCKET_KEY
SUPABASE_BUCKET_NAME
```
 – credentials for Supabase storage.

If these are not provided, the application will still start, but image upload and storage features will not work.  

For run application:  

```bash
#Delete old database - recomended for first use
docker compose down -v

@Compose backend with database
docker compose up --build
```

---

## 🛠 API Documentation & Health Monitoring
## 🔹 Swagger UI
After backend running you can interactive API documentation via Swagger:  
- URL: http://localhost:8080/swagger-ui/index.html  
- You can view all available endpoints, their request/response models, and try requests directly from the browser.  
<img width="1898" height="907" alt="image" src="https://github.com/user-attachments/assets/317ba305-df86-4d81-8bf3-ab8e2c7c6f3e" />


### 🔹 Spring Boot Actuator – Health Endpoint
The application provides a health monitoring endpoint using Spring Boot Actuator:  
- URL: http://localhost:8080/actuator/health  
- Example response:  
```
{
  "status": "UP"
}
```

You can use this to check if the backend is running properly, integrate with monitoring tools, or for simple uptime checks.

---

## 📝 Example API Endpoints

| Method | Endpoint                         | Description                      |
|--------|----------------------------------|----------------------------------|
| GET    | /business-page/{businessName}    | Get all business profile details |
| GET    | /events/{firstDayOfWeek}         | Get all events for given week    |
| GET    | /notification                    | Get all notifications            |
| DELETE | /events/{id}                     | Delete booking                   |
| GET    | /vacation/                       | Get list of user vacations       |
| POST   | /order                           | Create new booking               |

### Example Requests

```bash
curl -X GET http://localhost:8080/business-page/slotify

curl -X PUT http://localhost:8080/user \
-H "Authorization': `Bearer ${userToken}" \
-d '{"name":"User name","email":"example@mail.com"}'
```
---

### 🆘 Troubleshooting  
If you see “password authentication failed” when running with Docker:  
```
docker compose down -v
docker compose up --build
```

---

### 👤 Author
Filip Jarocki - [GitHub profile](https://github.com/Jaros-777)

---

### 🖥️ Other repos
Main repo - https://github.com/Jaros-777/Slotify  
Frontend repo - https://github.com/Jaros-777/slotify-frontend

## ⚖ License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

