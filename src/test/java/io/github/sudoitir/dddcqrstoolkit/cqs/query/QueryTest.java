package io.github.sudoitir.dddcqrstoolkit.cqs.query;

import io.github.sudoitir.dddcqrstoolkit.cqs.setup.*;
import io.github.sudoitir.dddcqrstoolkit.valueobject.ULID;
import org.junit.jupiter.api.BeforeEach;
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


@SpringBootTest (classes = {QueryTest.TestConfig.class})
@TestPropertySource (properties = "logging.level.io.github.sudoitir.dddcqrstoolkit.cqs.module=DEBUG")
class QueryTest {


    @Configuration
    @ComponentScan (basePackages = "io.github.sudoitir.dddcqrstoolkit")
    @Import ({GetProductQueryHandler.class, ExceptionalGetProductQueryHandler.class, InMemoryProductRepository.class})
    @EnableAspectJAutoProxy (proxyTargetClass = true)
    public static class TestConfig {
    }


    @Autowired
    private QueryBus queryBus;

    @Autowired
    private InMemoryProductRepository productRepository;

    private final ULID ulid = ULID.randomULID();

    @BeforeEach
    public void setUp() {
        Product product = new Product();
        product.setId(ulid);
        product.setName("Test Product");
        productRepository.save(product);
    }

    @Test
    void testHandleGetProductQuery() {
        GetProductQuery query = new GetProductQuery(ulid);

        Product result = queryBus.executeQuery(query);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(ulid);
        assertThat(result.getName()).isEqualTo("Test Product");
    }


    @Test
    void testHandleExceptionInExecute() {
        VoidProductQuery query = new VoidProductQuery();

        try {
            queryBus.executeQuery(query);
            fail("Should have thrown an exception");
        } catch (IllegalArgumentException ignored) {
        }
    }

}
