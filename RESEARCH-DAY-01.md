# RESEARCH DAY 01 - Category CRUD & Validation

### Q1. What does CRUD stand for?
**CRUD** stands for Create, Read, Update, and Delete. These represent the four foundational persistent operations that an application performs on a database storage system.

---

### Q2. Difference between HTTP methods POST, PUT, PATCH, DELETE?
* **POST**: Used to create a brand new resource records. It is non-idempotent (sending it multiple times duplicates data entries).
* **PUT**: Used to completely update or replace an existing resource record by sending the whole updated representation payload.
* **PATCH**: Used for minor, partial modifications to a resource record rather than replacing the entire model structure.
* **DELETE**: Used to permanently clear and remove a specified resource record from the storage server.

---

### Q3. Give the correct HTTP status code for each:
* **a. A new category was created**: `201 Created`
* **b. A category was deleted successfully**: `204 No Content`
* **c. The id requested does not exist**: `404 Not Found`
* **d. The request body is missing a required field**: `400 Bad Request`
* **e. The user is logged in but not allowed**: `403 Forbidden`

---

### Q4. Difference between @RequestBody, @RequestParam, @PathVariable
* **@RequestBody**: Maps the inbound JSON request body directly into a Java object parameter.
    * *Example*: `public ResponseEntity create(@RequestBody CategoryDto dto)`
* **@PathVariable**: Extracts dynamic values directly embedded within the URI pattern path string itself.
    * *Example*: `/api/categories/{id}` $\rightarrow$ `public ResponseEntity get(@PathVariable Long id)`
* **@RequestParam**: Extracts key-value pair query parameters appended at the tail end of a URL after a `?`.
    * *Example*: `/api/categories?page=0` $\rightarrow$ `public ResponseEntity list(@RequestParam int page)`

---

### Q5. What is Jakarta Bean Validation? Explain @Valid, @NotBlank, @Size.
**Jakarta Bean Validation** is a standard Java constraint validation specification framework interface.
* **@Valid**: Directs Spring Boot to trigger data constraint validation mechanisms immediately on an incoming method argument object structure.
* **@NotBlank**: Standard constraint ensuring string attributes cannot be null, completely empty, or filled only with empty blank whitespace.
* **@Size(min=x, max=y)**: Constraints the character length configuration string boundaries strictly within the min and max values.

---

### Q6. Why return a DTO and not the entity itself? Give 2 reasons.
1. **Security Hiding layer**: Avoids leaking raw database schema architectures or sensitive automated internal database audit fields directly to client browsers.
2. **Decoupling design separation**: Keeps client API endpoints stable. Changes to database mapping layout tables won't break client integration definitions.

---

### Q7. What is Optional<T>? Why does findById return Optional?
`Optional<T>` is a generic safety container wrapper object used to indicate either a present value state or an empty reference layout cleanly without using `null`. `findById` intentionally returns an `Optional` wrapper instance since a targeted record match might not exist for that database ID, forcing the developer code to explicitly check or throw custom exceptions.

---

## CONCEPTS SELF-QUIZ

### Q1. Why ResponseEntity instead of returning the object?
It provides complete flexibility to customize the exact outbound HTTP status response codes, payload body structures, and custom transaction metadata headers returned to clients.

### Q2. What status should a successful DELETE return? Why?
`204 No Content`. The resource deletion has executed successfully, so there is no remaining object model data to send back in the body payload.

### Q3. Update only one field - PUT or PATCH? Defend your answer.
**PATCH**. PUT explicitly expects a total overwrite payload representation of the entire resource data layer, whereas PATCH is semantic design specifically for minor field modifications.

### Q4. What happens if you forget @Valid on the controller?
Spring Boot will completely ignore your constraint annotations (`@NotBlank`, `@Size`), allowing empty or invalid payloads to bypass controls and run into execution layers unchecked.

### Q5. Why must update/delete have {id} in the URL but create does not?
Update and delete explicitly require targeting a specific, pre-existing identifier database row inside a collection. Creating acts on the entire general collection to append a brand new object instance.