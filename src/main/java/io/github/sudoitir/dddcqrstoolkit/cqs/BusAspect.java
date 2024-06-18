package io.github.sudoitir.dddcqrstoolkit.cqs;

import io.github.sudoitir.dddcqrstoolkit.cqs.module.ModuleRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BusAspect {

    private final ModuleRegistry moduleRegistry;


    public BusAspect(ModuleRegistry moduleRegistry) {
        this.moduleRegistry = moduleRegistry;
    }

    @Around("execution(* io.github.sudoitir.dddcqrstoolkit.cqs.command.SpringCommandBus.executeCommand(..)) ||"
            + " execution(* io.github.sudoitir.dddcqrstoolkit.cqs.query.SpringQueryBus.executeQuery(..))")
    public Object logAndValidate(ProceedingJoinPoint joinPoint) throws Throwable {
        Object arg = joinPoint.getArgs()[0];

        moduleRegistry.getBeforeExecutionModules()
                .forEach(busModule -> busModule.executeBefore(arg));

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            moduleRegistry
                    .getAfterThrowingModules()
                    .forEach(busModule -> busModule.executeAfterThrowing(arg, throwable));
            throw throwable;
        }
        moduleRegistry.getAfterExecutionModules().forEach(busModule -> busModule.executeAfter(arg));

        return result;
    }
}