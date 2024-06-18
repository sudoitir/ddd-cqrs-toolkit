package io.github.sudoitir.dddcqrstoolkit.cqs.module;

public interface AfterThrowingExecutionModule extends BusModule{
    void executeAfterThrowing(Object arg, Throwable throwable);

}
