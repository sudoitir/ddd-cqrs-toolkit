package io.github.sudoitir.dddcqrstoolkit.cqs.command;

import io.github.sudoitir.dddcqrstoolkit.cqs.setup.*;
import io.github.sudoitir.dddcqrstoolkit.cqs.strategy.HeaderBasedStrategyKeyProvider;
import io.github.sudoitir.dddcqrstoolkit.cqs.strategy.StrategyKeyProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
        @Bean
        public StrategyKeyProvider strategyKeyProvider(HttpServletRequest httpServletRequest) {
            return new HeaderBasedStrategyKeyProvider(httpServletRequest);
        }
    }


    @Autowired
    private CommandBus commandBus;
    @Autowired
    private InMemoryProductRepository productRepository;


    @Test
    void testCreateProduct() {
        String productName = "Test Product";
        CreateProductCommand command = new CreateProductCommand(productName);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("X-Strategy-Key", "key-1");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        CreateProductResult result = commandBus.executeCommand(command);

        assertThat(result).isNotNull();
        assertThat(result.message()).isEqualTo("Product created successfully key-1");

        Product savedProduct = productRepository.findById(result.productId()).orElseGet(Assertions::fail);
        assertThat(savedProduct.getName()).isEqualTo(productName);
    }


    @Test
    void testUpdateProduct() {
        CreateProductResult savedProduct = setup();

        String productName = "Update Product";

        UpdateProductCommand command = new UpdateProductCommand(savedProduct.productId(), productName);

        UpdateProductResult result = commandBus.executeCommand(command);

        assertThat(result).isNotNull();
        assertThat(result.message()).isEqualTo("Product updated successfully");
    }

    private CreateProductResult setup() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("X-Strategy-Key", "key-1");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        String productName = "Test Product";
        CreateProductCommand command = new CreateProductCommand(productName);
        return commandBus.executeCommand(command);
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
