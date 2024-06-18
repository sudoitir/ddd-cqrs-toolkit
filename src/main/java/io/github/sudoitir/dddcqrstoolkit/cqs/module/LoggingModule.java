package io.github.sudoitir.dddcqrstoolkit.cqs.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingModule implements
        BeforeExecutionModule,
        AfterExecutionModule,
        AfterThrowingModule,
        BusTypeProvider {

    private static final Logger logger = LoggerFactory.getLogger(LoggingModule.class);


    @Override
    public void executeBefore(final Object arg) {
        String busTypeName = getBusTypeName(arg);
        logger.info("Executing {}: {}", busTypeName, arg.getClass().getSimpleName());
    }

    @Override
    public void executeAfter(final Object arg) {
        String busTypeName = getBusTypeName(arg);
        logger.info("{} executed successfully: {}", busTypeName, arg.getClass().getSimpleName());

    }

    @Override
    public void executeAfterThrowing(final Object arg, Throwable throwable) {
        String busTypeName = getBusTypeName(arg);
        logger.error("Error executing {}: {}", busTypeName, arg.getClass().getSimpleName(),
                throwable);
    }
}
