package io.github.sudoitir.dddcqrstoolkit.cqs.module;

import io.micrometer.core.instrument.Counter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MicrometerCountingModule implements BeforeExecutionModule {

    private final CounterFactory counterFactory;
    private final Map<Class<?>, Counter> counters = new ConcurrentHashMap<>();

    public MicrometerCountingModule(CounterFactory counterFactory) {
        this.counterFactory = counterFactory;
    }

    @Override
    public void executeBefore(final Object arg) {
        counters.computeIfAbsent(arg.getClass(), counterFactory::create).increment();
    }

    /**
     * Factory for creating Micrometer counters for the given command class.
     */
    @FunctionalInterface
    public interface CounterFactory {

        /**
         * Create counter for given arg class.
         *
         * @param arg class of arg
         * @return Micrometer counter
         */
        Counter create(Class<?> arg);
    }

}
