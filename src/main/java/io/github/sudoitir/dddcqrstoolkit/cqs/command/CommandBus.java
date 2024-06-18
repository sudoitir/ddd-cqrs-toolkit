package io.github.sudoitir.dddcqrstoolkit.cqs.command;

public interface CommandBus {

    <R, C extends Command<R>> R executeCommand(C command);
}
