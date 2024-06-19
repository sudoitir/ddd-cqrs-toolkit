package io.github.sudoitir.dddcqrstoolkit.cqs.setup;

import io.github.sudoitir.dddcqrstoolkit.cqs.command.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateProductHandler implements CommandHandler<CreateProductResult, CreateProductCommand> {

    @Autowired
    private InMemoryProductRepository productRepository;

    @Override
    public CreateProductResult handle(CreateProductCommand command) {
        Product product = new Product();
        product.setName(command.name());
        Product savedProduct = productRepository.save(product);
        return new CreateProductResult(savedProduct.getId(), "Product created successfully");
    }
}
