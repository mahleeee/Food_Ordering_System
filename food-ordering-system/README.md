Food Ordering System - Development Environment & Architecture Setup

Part 4: Project Investigation (Research Questions)

1. What is Spring Boot?
Spring Boot is an extension of the Spring framework that simplifies the process of building production-grade, stand-alone Java applications. It eliminates massive amounts of boilerplate configuration by using "auto-configuration," allowing developers to just start coding immediately.

2. What is Maven?
Maven is a build automation and project management tool primarily used for Java projects. It manages a project's lifecycle, compiles code, runs tests, and automates packaging.

 3. What is the purpose of pom.xml?
The `pom.xml` (Project Object Model) file is the configuration heart of a Maven project. It acts as a manifest file where developers declare project metadata, the required Java version, and all external dependencies (libraries) needed for the application to run.

4. What is the purpose of application.properties?
`application.properties` is the master configuration file for a Spring Boot application. It is used to define environment-specific variables, such as server ports, database connection URLs, usernames, passwords, and logging configurations.

5. What does @SpringBootApplication do?
The `@SpringBootApplication` annotation is a convenience annotation that triggers three core features:
= `@SpringBootConfiguration`: Marks the class as a source of bean definitions.
 =`@EnableAutoConfiguration`: Tells Spring Boot to start adding beans based on classpath settings.
 =`@ComponentScan`: Tells Spring Boot to look for controllers, services, and other components in the current package and its sub-packages.

6. Why do developers use dependency management tools such as Maven?
Developers use tools like Maven to avoid manually downloading, updating, and managing external `.jar` files. Maven automatically downloads the required libraries from a central repository, handles version compatibility, and manages transitive dependencies (dependencies that your libraries depend on).

7. What is a REST API?
A REST (Representational State Transfer) API is an architectural style for designing networked applications. It relies on a stateless, client-server communication protocol—almost always HTTP—using standard methods like GET, POST, PUT, and DELETE to manipulate resources.

8. What is JSON?
JSON (JavaScript Object Notation) is a lightweight, text-based data interchange format. It is easy for humans to read and write, and easy for machines to parse and generate, making it the standard format for transferring data between a server and a web application.

9. What is Dependency Injection?
Dependency Injection (DI) is a design pattern used to implement Inversion of Control (IoC). Instead of a class manually creating or instantiating its dependencies (the objects it needs to function) using the `new` keyword, the framework creates them and hands ("injects") them into the class, making the code loosely coupled and highly testable.



Part 5: Package Structure Explanation

1.controller: The web layer. It contains REST controllers that expose API endpoints, listen for incoming HTTP requests, and return responses back to the client.
2.service: The business logic layer. It coordinates data processing, calculations, and rules, serving as the bridge between the controllers and repositories.
3.repository: The data access layer. It interacts directly with the database, extending Spring Data JPA interfaces to run CRUD operations without writing raw SQL.
4.entity: The domain layer. It contains standard Java objects that map directly to the database tables using JPA annotations.
5.dto (Data Transfer Object): The data presentation layer. Used to transfer data safely between layers or across the network without exposing internal database structures directly.
6.config: The configuration layer. Holds classes annotated with `@Configuration` to define custom Spring beans and app setups.
7.exception: The error handling layer. Contains custom exception classes and global handlers to return clean, user-friendly error responses when something goes wrong.