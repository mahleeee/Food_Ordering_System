# Day 03 Research — Menu Feature Architectural Questions

### Q1. What is JPA? What is Hibernate? How are they related?
JPA (Jakarta Persistence API) is a standard specification or blueprint that defines how Java objects map to relational databases. Hibernate is the actual framework implementation that does the heavy lifting under the hood to carry out those database operations.

### Q2. What is the difference between @Entity and @Table?
`@Entity` is a JPA marker annotation that tells Spring Boot this class represents a database table model. `@Table(name = "menus")` is an optional annotation used to customize the exact name of the table created inside the MySQL database.

### Q3. What is a foreign key? What is @ManyToOne? Give 2 real-world examples.
A foreign key is a column that links data in one table to the primary key of another table to maintain relationships. `@ManyToOne` defines this relationship in Java, showing that multiple rows of this entity can belong to one parent entity.
* **Example 1:** Multiple `Menu` items belonging to one `Category`.
* **Example 2:** Multiple `Order` records belonging to one `User`.

### Q4. What does @JoinColumn(name = "category_id") do?
It explicitly names the physical foreign key column (`category_id`) inside the database table that handles the relationship link.

### Q5. Why store price as BigDecimal and not double?
Floating-point data types like `double` introduce rounding errors due to how binary fractional values are calculated. `BigDecimal` provides exact mathematical precision, making it non-negotiable for financial or monetary values.

### Q6. What does FetchType LAZY vs EAGER mean? What is the default for @ManyToOne?
* **EAGER:** Loads the relationship data instantly alongside the parent entity from the database.
* **LAZY:** Postpones fetching the relationship data until it is explicitly accessed in the code, which optimizes performance.
* **Default:** `@ManyToOne` defaults to `EAGER`.

### Q7. What is the N+1 query problem?
This performance bottleneck occurs when you execute 1 query to fetch a list of parent entities, and the application then triggers `N` individual separate queries to fetch the associated relationship details for each row returned in that list.

### Q8. What is dependency injection? Constructor injection vs field injection — which is preferred and why?
Dependency Injection is an architectural pattern where the framework provides objects their required dependencies rather than forcing them to instantiate them manually. Constructor injection is highly preferred over field injection because it ensures dependencies are immutable (`final`), prevents runtime null pointer errors, and makes testing simpler.

### Q9. What does @RequiredArgsConstructor (Lombok) do?
It automatically generates a constructor at compile time for all fields marked as `private final`, which simplifies writing clean constructor injection code.

### Q10. What is the role of the SERVICE layer? Why must it be separate from the controller?
The Service layer handles the core business logic, data transformations, and transactional rules of the application. Keeping it separate from the Controller ensures clear separation of concerns, keeping the API endpoints thin and allowing the business logic to be reused or tested independently.

### Q11. Why MUST you validate that categoryId exists before saving a menu?
To prevent orphan records, application runtime crashes, or data integrity failures. If a menu is saved with a non-existent category ID, the database will reject the operation with a foreign key constraint violation.

### Q12. Difference between save() and saveAndFlush()?
* `save()` holds changes in an in-memory transactional cache and writes them to the database during the final transaction commit phase.
* `saveAndFlush()` pushes the changes to the database instantly, executing the SQL statement immediately without waiting for the transaction to end.

### Q13. Why write private mapper methods (entity <-> dto)?
Mappers isolate database-specific structures (`Entity`) from public API-exposed contracts (`DTO`). Writing them keeping them private prevents leakage of implementation details and prevents data over-exposure.