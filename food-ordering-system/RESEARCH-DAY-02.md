# Day 02 Research: Standard Response Wrapper

### Q1. What is a Java generic type? Why is <T> useful?
A generic type is a feature that allows classes, interfaces, and methods to be parameterized with types. The `<T>` placeholder is incredibly useful because it allows us to reuse a single wrapper class (`Response<T>`) for any data payload (e.g., `CategoryDto`, `ProductDto`, or lists) while maintaining strict compile-time type safety without relying on risky type-casting or raw `Object` classes.

### Q2. What does Lombok @Builder generate behind the scenes?
Behind the scenes, Lombok generates an inner static class (typically named `ResponseBuilder`) along with setter-like methods for each field that return the builder instance itself (enabling method chaining). It also generates a private constructor for the target class and a final `.build()` method that instantiates the class with the collected parameters.

### Q3. What is the Builder design pattern? When to use it?
The Builder pattern is a creational design pattern designed to provide a flexible solution to various object construction problems. It should be used when an object has a large number of optional fields, requires step-by-step construction, or when we want to make the target object immutable by avoiding public setter methods.

### Q4. What is LocalDateTime? How is it different from Date?
`LocalDateTime` is part of the modern, immutable, and thread-safe `java.time` API introduced in Java 8. Unlike the old legacy `java.util.Date`, it represents a clear date-time value without a timezone context, has cleaner formatting methods, and does not suffer from design flaws like zero-based indexing for months.

### Q5. Why does a consistent response format matter to frontend developers?
A consistent top-level response envelope allows frontend developers to build standardized, reusable interceptors and data-parsing utilities. They can universally read the `statusCode` and metadata before pulling out the dynamic `data` property, resulting in significantly cleaner UI logic and predictable global error handling.

### Q6. What does @JsonInclude(JsonInclude.Include.NON_NULL) do?
This Jackson annotation ensures that fields containing a `null` value are completely omitted from the generated JSON string. For instance, in our `success` responses, an empty or irrelevant `error` field won't be sent over the wire, keeping our JSON payloads exceptionally clean and compact.

### Q7. What is a static factory method? Why use Response.success(...) instead of new Response<>()?
A static factory method is a static method encapsulated inside a class that returns an instance of that class. Using `Response.success(...)` improves code readability (expresses exact intent), abstracts away initialization values like `LocalDateTime.now()`, and avoids explicit duplication of complex generic diamond operators during object instantiation.

## End of Day 02 Research
All tasks completed and verified against the checklist.