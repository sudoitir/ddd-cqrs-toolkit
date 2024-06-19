package io.github.sudoitir.dddcqrstoolkit.cqs.setup;

import io.github.sudoitir.dddcqrstoolkit.cqs.query.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetProductQueryHandler implements QueryHandler<Product, GetProductQuery> {

    @Autowired
    private InMemoryProductRepository productRepository;

    @Override
    public Product handle(GetProductQuery query) {
        return productRepository.findById(query.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }
}
