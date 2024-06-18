package io.github.sudoitir.dddcqrstoolkit.cqs.module;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationModule implements BeforeExecutionModule, BusTypeProvider {

    private static final Logger logger = LoggerFactory.getLogger(ValidationModule.class);

    private final Validator validator;

    public ValidationModule(Validator validator) {
        this.validator = validator;
    }

    @Override
    public void executeBefore(final Object arg) {
        Set<ConstraintViolation<Object>> violations = validator.validate(arg);
        if (!violations.isEmpty()) {
            if (logger.isDebugEnabled()) {
                violations.forEach(
                        violation -> logger.error("Validation error: {}", violation.getMessage()));
            }
            throw new ConstraintViolationException(violations);
        }
    }

}
