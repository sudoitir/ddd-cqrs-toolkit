package io.github.sudoitir.dddcqrstoolkit.cqs.setup;


import io.github.sudoitir.dddcqrstoolkit.cqs.command.Command;
import io.github.sudoitir.dddcqrstoolkit.valueobject.ULID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateProductCommand(@NotNull ULID id, @NotBlank String name) implements Command<UpdateProductResult> {

}
