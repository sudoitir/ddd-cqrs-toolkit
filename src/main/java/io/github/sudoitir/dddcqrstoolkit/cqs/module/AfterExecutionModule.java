package io.github.sudoitir.dddcqrstoolkit.cqs.module;

public interface AfterExecutionModule extends BusModule {

    void executeAfter(Object arg);

}
