package io.github.sudoitir.dddcqrstoolkit.cqs.setup;

import io.github.sudoitir.dddcqrstoolkit.ulid.ULID;

public record CreateProductResult(ULID productId, String message) {
}
