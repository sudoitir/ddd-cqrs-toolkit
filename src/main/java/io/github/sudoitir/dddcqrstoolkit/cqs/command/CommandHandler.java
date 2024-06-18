package io.github.sudoitir.dddcqrstoolkit.cqs.command;

public interface CommandHandler<R, C extends Command<R>> {

    R handle(C command);
}
