# DDD CQRS Toolkit

[![Java CI with Maven](https://github.com/sudoitir/ddd-cqrs-toolkit/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/sudoitir/ddd-cqrs-toolkit/actions/workflows/maven.yml)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=sudoitir_ddd-cqrs-toolkit&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=sudoitir_ddd-cqrs-toolkit)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=sudoitir_ddd-cqrs-toolkit&metric=bugs)](https://sonarcloud.io/summary/new_code?id=sudoitir_ddd-cqrs-toolkit)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=sudoitir_ddd-cqrs-toolkit&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=sudoitir_ddd-cqrs-toolkit)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=sudoitir_ddd-cqrs-toolkit&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=sudoitir_ddd-cqrs-toolkit)



A Java library for implementing Domain-Driven Design (DDD) and Command Query Responsibility Segregation (CQRS) patterns
using Spring Framework.

## Features

- Command and Query Buses for handling operations.
- Command and Query Handlers for encapsulating business logic.
- Support for modular logging, validation, and more.
- Integration with Spring's application context.

## Installation

After installing the project with Maven using `mvn install`, add the following dependency to your `pom.xml`:

```xml

<dependency>
    <groupId>io.github.sudoitir</groupId>
    <artifactId>dddcqrstoolkit</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Getting Started

If needed, you can configure the Bus with more modules. For example, see the following configuration:
[BusConfig.java](https://github.com/sudoitir/ddd-cqrs-toolkit/blob/93a26b140493fce42d514e7f23001fa348ac4554/src/main/java/io/github/sudoitir/dddcqrstoolkit/cqs/config/BusConfig.java)

## Command Example

### Define a Command and Command Handler

```java
public class CreateProductCommand implements Command<CreateProductResult> {
    private String productName;
    // getters and setters
}


import org.springframework.stereotype.Component;

@Component
public class CreateProductCommandHandler implements CommandHandler<CreateProductResult, CreateProductCommand> {
    @Override
    public CreateProductResult handle(CreateProductCommand command) {
        // Business logic to create a product
        return new CreateProductResult(/*...*/);
    }
}
```

### Command Bus

```java

@RestController
@RequestMapping ("/api/products")
public class ProductCommandController {

    private final CommandBus commandBus;

    @Autowired
    public ProductCommandController(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @PostMapping
    public ResponseEntity<CreateProductResult> createProduct(@RequestBody CreateProductCommand command) {
        CreateProductResult result = commandBus.executeCommand(command);
        return ResponseEntity.ok(result);
    }
}
```

## Query Example

### Define Query and Query Handler

```java
public class GetProductQuery implements Query<Product> {
    private Long productId;
    // getters and setters
}

import org.springframework.stereotype.Component;

@Component
public class GetProductQueryHandler implements QueryHandler<Product, GetProductQuery> {
    @Override
    public Product handle(GetProductQuery query) {
        // Business logic to get a product
        return new Product(/*...*/);
    }
}

```

### Query Bus

```java

@RestController
@RequestMapping ("/api/products")
public class ProductQueryController {

    private final QueryBus queryBus;

    @Autowired
    public ProductQueryController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        GetProductQuery query = new GetProductQuery();
        query.setProductId(id);
        Product product = queryBus.executeQuery(query);
        return ResponseEntity.ok(product);
    }
}
```
