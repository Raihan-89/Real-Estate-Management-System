# REMS - Real Estate Management System
A web application built with **Java & Spring Boot** to store and manage users, properties and other details.

##Features:

- Create user account.
- Create property listing.
- CRUD operation for users and property listing.

## How to run:

- Download the project.
- Open the project in Intellij IDEA.
- Update the `application.properties`:
- Add your MongoDB Atlas address and database name to `application.properties` by replacing the below code:
  ```bash
  spring.data.mongodb.uri=mongodb+srv://java:<password>@advanced-java.bklev.mongodb.net/?retryWrites=true&w=majority&appName=advanced-java
  spring.data.mongodb.database=remsDb
- Set a pre-defined ADMIN database to access the ADMIN features of the website.
- Example:
  ```bash
  username: "raihan"
  email: "raihan@xyz.com"
  password: "123"
  userType: "ADMIN"
