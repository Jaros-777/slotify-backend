# Slotify backend

## 🛠 Technologies  
- Java 
- SpringBoot  

## 🚀 Getting Started  
This section explains how to run the Slotify Backend project locally.  

### ✅ Prerequisites  
- Java 17 or later  
- Maven (or use your IDE’s built-in Maven support)  
- PostgreSQL (recommended for production)  
- (optional) H2 Database (used for testing or local development)  
- Git (optional, for cloning the repository)  
 
### 📦 Installation  
1. Clone the repository:  
`git clone https://github.com/Jaros-777/slotify-backend.git`
  
2. Navigate to the project directory:  
`cd slotify-backend`
  
3. Open the project in your IDE  
(e.g. IntelliJ IDEA, Eclipse, or VS Code with Java extensions)  

4. ⚙️ Configuration  
Create an .env or application.properties file (if not already provided) and configure your database connection:  
Example for PostgreSQL:  
```java
spring.application.name=slotify-backend

#set database name
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
#set database user name
spring.datasource.username=${DB_USERNAME:your_username}
#set database user password
spring.datasource.password=$${DB_PASSWORD:your_password}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

spring.jpa.properties.hibernate.default_schema=public
```
 
4. 🖥️ Running the development server  
`mvn spring-boot:run`  
  
Once running, the API will be available at:  
👉 http://localhost:8080

## ⚖ License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.
