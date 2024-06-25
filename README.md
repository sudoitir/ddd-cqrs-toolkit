# DDD CQRS Toolkit

[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=sudoitir_ddd-cqrs-toolkit&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=sudoitir_ddd-cqrs-toolkit)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=sudoitir_ddd-cqrs-toolkit&metric=bugs)](https://sonarcloud.io/summary/new_code?id=sudoitir_ddd-cqrs-toolkit)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=sudoitir_ddd-cqrs-toolkit&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=sudoitir_ddd-cqrs-toolkit)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=sudoitir_ddd-cqrs-toolkit&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=sudoitir_ddd-cqrs-toolkit)

A Java library for implementing Domain-Driven Design (DDD) and Command Query Responsibility
Segregation (CQRS) patterns
using Spring Framework.

## Features

- Command and Query Buses for handling operations.
- Command and Query Handlers for encapsulating business logic.
- Support for modular logging, validation, and more.
- Integration with Spring's application context.

### CommandBus Strategy Selector

The `CommandBus` strategy selector allows for dynamic selection of command handlers based on a
strategy key. This is particularly useful when multiple implementations of a command handler exist.

- **Dynamic Command Handler Selection**: Choose the appropriate command handler based on a strategy
  key.
- **Custom Strategy Key Providers**: Implement the `StrategyKeyProvider` interface to provide custom
  strategy keys.
- **Default Header-Based Strategy Key Provider**: Use the `HeaderBasedStrategyKeyProvider` for
  HTTP-based strategy key extraction.
- **Annotation-Based Strategy Assignment**: Use the `@StrategyKey` annotation to mark command
  handler implementations.

## Installation

Add the following dependency to your `pom.xml`:
[Central Sonatype](https://central.sonatype.com/artifact/io.github.sudoitir/dddcqrstoolkit/overview)

```xml

<dependency>
  <groupId>io.github.sudoitir</groupId>
  <artifactId>dddcqrstoolkit</artifactId>
  <version>1.2.4</version>
</dependency>
```

## Getting Started

If needed, you can configure the Bus with more modules. For example, see the following
configuration:
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
public class CreateProductCommandHandler implements
        CommandHandler<CreateProductResult, CreateProductCommand> {

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
@RequestMapping("/api/products")
public class ProductCommandController {

    private final CommandBus commandBus;

    @Autowired
    public ProductCommandController(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @PostMapping
    public ResponseEntity<CreateProductResult> createProduct(
            @RequestBody CreateProductCommand command) {
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
@RequestMapping("/api/products")
public class ProductQueryController {

    private final QueryBus queryBus;

    @Autowired
    public ProductQueryController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        GetProductQuery query = new GetProductQuery();
        query.setProductId(id);
        Product product = queryBus.executeQuery(query);
        return ResponseEntity.ok(product);
    }
}
```

### Usage Of Strategy selector

**Only adding bean of StrategyKeyProvider can enable strategy selector feature.**

#### 1. Define a Strategy Key Provider

To use the strategy selector feature, you need to define a bean of the `StrategyKeyProvider`
interface. You can either implement your own or use the provided `HeaderBasedStrategyKeyProvider`.

##### Header based strategy key provider

Add following Bean to a configuration class:

```java

@Bean
public StrategyKeyProvider strategyKeyProvider(HttpServletRequest httpServletRequest) {
    return new HeaderBasedStrategyKeyProvider(httpServletRequest);
}
```

##### Custom Strategy Key Provider

Implement the `StrategyKeyProvider` interface:

```java
import org.springframework.stereotype.Component;

@Component
public class CustomStrategyKeyProvider implements StrategyKeyProvider {

    @Override
    public String getStrategyKey() {
        // Custom logic to provide strategy key
        return "your-custom-key";
    }
}
```
