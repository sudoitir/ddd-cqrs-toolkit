package io.github.sudoitir.dddcqrstoolkit.cqs.module;

public interface AfterThrowingModule extends BusModule {

    void executeAfterThrowing(Object arg, Throwable throwable);

}
