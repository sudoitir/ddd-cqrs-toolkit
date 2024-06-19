package io.github.sudoitir.dddcqrstoolkit.cqs.setup;

import io.github.sudoitir.dddcqrstoolkit.cqs.command.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateProductHandler implements CommandHandler<UpdateProductResult, UpdateProductCommand> {

    @Autowired
    private InMemoryProductRepository productRepository;

    @Override
    public UpdateProductResult handle(UpdateProductCommand command) {
        Product product = new Product();
        product.setName(command.name());
        Product updatedProduct = productRepository.update(command.id(), product);
        return new UpdateProductResult(updatedProduct.getId(), "Product updated successfully");
    }
}
