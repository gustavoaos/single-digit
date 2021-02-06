# Single Digit Service

This is a REST based service that allows API consumers to CRUD user and compute single digit for them.

This project was develop focused on unity tests using a Clean Architecture approach.

The APIs invoke a single **Use Case**. The **Use Case** constructs and acts over one to many **Entities**
through **Repositories**. Separate models are used for each layer when necessary.

There are 6 public APIs
- Create User ```POST /users/api/v1/users```
- Find User by ID ```GET /users/api/v1/users/{user-id}```
- Delete User ```DELETE /users/api/v1/users/{user-id}```
- Update User ```PUT /users/api/v1/users/{user-id}```
- Compute Single Digit ```GET /users/api/v1/users/compute?id={user-id}```
- Get Single Digit by User ID ```GET /users/api/v1/users/{user-id}/list```


## Project dependencies
This project uses Java 8 with Spring Boot, H2 database and Swagger for API documentation.
Tests were written with JUnit5 and AssertJ.

## Running the application
To build the project, run:

```shell
mvn clean install spring-boot:repackage
```

To run all tests, run:

```shell
mvn test
```

To run the application, from the root of the project run:
```shell
mvn spring-boot:run
```
or
```shell
java -jar target/social-media-list-0.0.1-SNAPSHOT.jar
```

View the [Swagger documentation](http://localhost:8080/swagger-ui/single-digit.html)
