package io.github.sudoitir.dddcqrstoolkit.cqs;

public interface CommandHandler<R, C extends Command<R>> {

    R handle(C command);
}
