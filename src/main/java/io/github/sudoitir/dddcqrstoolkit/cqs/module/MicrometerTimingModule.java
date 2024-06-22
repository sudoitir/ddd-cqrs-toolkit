package io.github.sudoitir.dddcqrstoolkit.cqs.module;

import io.micrometer.core.instrument.Timer;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A module for timing execution using Micrometer.
 */
public class MicrometerTimingModule implements BeforeExecutionModule, AfterExecutionModule,
        AfterThrowingModule {

    private final TimerFactory timerFactory;
    private final Clock clock;
    private final Map<Object, TimerInfo> timerMap = new ConcurrentHashMap<>();
    private final Map<Class<?>, Timer> timers = new ConcurrentHashMap<>();

    public MicrometerTimingModule(TimerFactory timerFactory) {
        this.timerFactory = timerFactory;
        this.clock = Clock.systemDefaultZone();
    }

    @Override
    public void executeBefore(Object arg) {
        Timer timer = timers.computeIfAbsent(arg.getClass(), timerFactory::create);
        Instant now = Instant.now(clock);
        timerMap.put(arg, new TimerInfo(now, timer));
    }

    @Override
    public void executeAfter(Object arg) {
        TimerInfo timerInfo = timerMap.remove(arg);
        if (timerInfo != null) {
            timerInfo.timer.record(Duration.between(timerInfo.startTime, Instant.now(clock)));
        }
    }

    @Override
    public void executeAfterThrowing(Object arg, Throwable throwable) {
        timerMap.remove(arg);
    }

    /**
     * Factory for creating Micrometer timers for the given class.
     */
    @FunctionalInterface
    public interface TimerFactory {

        /**
         * Creates a timer for the specified class.
         *
         * @param clazz the class to create a timer for
         * @return the created timer
         */
        Timer create(Class<?> clazz);
    }

    /**
     * Holds information about a timer and its start time.
     */
    private record TimerInfo(Instant startTime, Timer timer) {

    }
}
