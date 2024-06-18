package io.github.sudoitir.dddcqrstoolkit.cqs.module;

import io.github.sudoitir.dddcqrstoolkit.cqs.command.Command;

public interface BusTypeProvider extends BusModule {

    default String getBusTypeName(final Object arg) {
        if (arg instanceof Command) {
            return "Command";
        }
        return "Query";
    }

}
