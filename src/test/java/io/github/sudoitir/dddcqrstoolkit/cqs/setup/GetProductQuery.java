package io.github.sudoitir.dddcqrstoolkit.cqs.setup;


import io.github.sudoitir.dddcqrstoolkit.cqs.query.Query;
import io.github.sudoitir.dddcqrstoolkit.ulid.ULID;
import jakarta.validation.constraints.NotNull;

public record GetProductQuery(@NotNull ULID productId) implements Query<Product> {

}
