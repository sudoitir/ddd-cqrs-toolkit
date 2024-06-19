package io.github.sudoitir.dddcqrstoolkit.cqs.command;

import io.github.sudoitir.dddcqrstoolkit.cqs.command.setup.*;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;


@SpringBootTest (classes = {CommandTest.TestConfig.class})
@TestPropertySource (properties = "logging.level.io.github.sudoitir.dddcqrstoolkit.cqs.module=DEBUG")
class CommandTest {

    @Configuration
    @ComponentScan (basePackages = "io.github.sudoitir.dddcqrstoolkit")
    @Import ({CreateProductHandler.class, InMemoryProductRepository.class})
    @EnableAspectJAutoProxy (proxyTargetClass = true)
    public static class TestConfig {
    }


    @Autowired
    private CommandBus commandBus;
    @Autowired
    private InMemoryProductRepository productRepository;


    @Test
    void testCreateProduct() {
        String productName = "Test Product";
        CreateProductCommand command = new CreateProductCommand(productName);

        CreateProductResult result = commandBus.executeCommand(command);

        assertThat(result).isNotNull();
        assertThat(result.message()).isEqualTo("Product created successfully");

        Product savedProduct = productRepository.findById(result.productId()).orElseGet(Assertions::fail);
        assertThat(savedProduct.getName()).isEqualTo(productName);

    }


    @Test
    void testValidationOfCreateProduct() {
        String productName = "";
        CreateProductCommand command = new CreateProductCommand(productName);
        try {
            commandBus.executeCommand(command);
            fail("Should have thrown an exception");
        } catch (ConstraintViolationException ignored) {

        }

    }
}
