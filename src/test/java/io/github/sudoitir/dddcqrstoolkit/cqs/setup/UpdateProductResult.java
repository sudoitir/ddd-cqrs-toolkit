package io.github.sudoitir.dddcqrstoolkit.cqs.setup;

import io.github.sudoitir.dddcqrstoolkit.ulid.ULID;

public record UpdateProductResult(ULID productId, String message) {
}
