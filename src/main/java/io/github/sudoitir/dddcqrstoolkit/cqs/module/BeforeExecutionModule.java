package io.github.sudoitir.dddcqrstoolkit.cqs.module;

public interface BeforeExecutionModule extends BusModule {

    void executeBefore(Object arg);

}
