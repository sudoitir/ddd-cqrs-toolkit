package io.github.sudoitir.dddcqrstoolkit.cqs.command.setup;


import io.github.sudoitir.dddcqrstoolkit.cqs.command.Command;
import jakarta.validation.constraints.NotBlank;

public record CreateProductCommand(@NotBlank String name) implements Command<CreateProductResult> {

}
