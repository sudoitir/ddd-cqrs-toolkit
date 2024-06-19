package io.github.sudoitir.dddcqrstoolkit.cqs.setup;

import io.github.sudoitir.dddcqrstoolkit.valueobject.ULID;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryProductRepository {

    private final Map<ULID, Product> products = new HashMap<>();

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(ULID.randomULID());
        }
        products.put(product.getId(), product);
        return product;
    }

    public Optional<Product> findById(ULID ulid) {
        return Optional.ofNullable(products.get(ulid));
    }

    public Iterable<Product> findAll() {
        return products.values();
    }

    public void deleteById(ULID ulid) {
        products.remove(ulid);
    }
}
