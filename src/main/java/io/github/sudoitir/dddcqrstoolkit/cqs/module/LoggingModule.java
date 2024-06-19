package io.github.sudoitir.dddcqrstoolkit.cqs.module;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class LoggingModule implements
                           BeforeExecutionModule,
                           AfterExecutionModule,
                           AfterThrowingModule,
                           BusTypeProvider {

    private static final Logger logger = LoggerFactory.getLogger(LoggingModule.class);


    @Override
    public void executeBefore(final Object arg) {
        String busTypeName = getBusTypeName(arg);
        if (logger.isDebugEnabled()) {
            logger.debug("Executing {}: {} with details: {}", busTypeName, arg.getClass().getSimpleName(), arg);
        } else {
            logger.info("Executing {}: {}", busTypeName, arg.getClass().getSimpleName());
        }
    }

    @Override
    public void executeAfter(final Object arg) {
        String busTypeName = getBusTypeName(arg);
        if (logger.isDebugEnabled()) {
            logger.debug("{} executed successfully: {} with details: {}", busTypeName, arg.getClass().getSimpleName(), arg);
        } else {
            logger.info("{} executed successfully: {}", busTypeName, arg.getClass().getSimpleName());
        }
    }

    @Override
    public void executeAfterThrowing(final Object arg, Throwable throwable) {
        String busTypeName = getBusTypeName(arg);
        if (logger.isDebugEnabled()) {
            if (throwable instanceof ConstraintViolationException constraintViolationException) {
                logValidationErrors(constraintViolationException);
            }
            logger.debug("Error executing {}: {} with details: {}",
                    busTypeName,
                    arg.getClass().getSimpleName(),
                    arg,
                    throwable);
        } else {
            logger.error("Error executing {}: {}", busTypeName, arg.getClass().getSimpleName(), throwable);
        }

    }

    private void logValidationErrors(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
        constraintViolations.forEach(violation ->
                logger.error("Validation error on field '{}': {}", violation.getPropertyPath(), violation.getMessage())
        );
    }
}
