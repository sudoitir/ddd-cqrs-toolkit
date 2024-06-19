package io.github.sudoitir.dddcqrstoolkit.cqs.command.setup;

import io.github.sudoitir.dddcqrstoolkit.valueobject.ULID;

public record CreateProductResult(ULID productId, String message) {
}
