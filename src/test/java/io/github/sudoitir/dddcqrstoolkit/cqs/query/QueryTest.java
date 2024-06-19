package io.github.sudoitir.dddcqrstoolkit.cqs.query;

import io.github.sudoitir.dddcqrstoolkit.cqs.setup.GetProductQuery;
import io.github.sudoitir.dddcqrstoolkit.cqs.setup.GetProductQueryHandler;
import io.github.sudoitir.dddcqrstoolkit.cqs.setup.InMemoryProductRepository;
import io.github.sudoitir.dddcqrstoolkit.cqs.setup.Product;
import io.github.sudoitir.dddcqrstoolkit.valueobject.ULID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.relational.core.mapping.event.AfterSaveEvent;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest (classes = {QueryTest.TestConfig.class})
@TestPropertySource (properties = "logging.level.io.github.sudoitir.dddcqrstoolkit.cqs.module=DEBUG")
class QueryTest {


    @EnableJdbcAuditing

    @Configuration
    @ComponentScan (basePackages = "io.github.sudoitir.dddcqrstoolkit")
    @Import ({GetProductQueryHandler.class, InMemoryProductRepository.class})
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

}
