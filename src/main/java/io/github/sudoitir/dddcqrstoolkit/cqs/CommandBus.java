package io.github.sudoitir.dddcqrstoolkit.cqs;

public interface CommandBus {

    <R, C extends Command<R>> R executeCommand(C command);
}
