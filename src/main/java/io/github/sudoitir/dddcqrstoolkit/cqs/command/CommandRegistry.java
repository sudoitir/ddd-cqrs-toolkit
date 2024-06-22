package io.github.sudoitir.dddcqrstoolkit.cqs.command;

import io.github.sudoitir.dddcqrstoolkit.cqs.strategy.StrategyKey;
import io.github.sudoitir.dddcqrstoolkit.cqs.strategy.StrategyKeyProvider;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.GenericTypeResolver;


/**
 * Registry holds the mapping between a command and its handler. The registry should always be
 * injected, by the spring framework.
 */
public class CommandRegistry implements ApplicationListener<ContextRefreshedEvent> {

    private final Map<Class<? extends Command<?>>, CommandProvider<CommandHandler<?, ?>>> commandProviderMap = new HashMap<>();

    @Autowired(required = false)
    private StrategyKeyProvider strategyKeyProvider;

    public CommandRegistry() {
    }


    public <R, C extends Command<R>> CommandHandler<R, C> getCommandHandler(Class<C> commandClass) {
        CommandProvider<CommandHandler<?, ?>> commandProvider = commandProviderMap.get(
                commandClass);

        if (commandProvider == null) {
            throw new IllegalArgumentException(
                    "No command provider found for " + commandClass.getName());
        }

        if (strategyKeyProvider != null && commandProvider.isStrategy()) {
            String strategyKey = strategyKeyProvider.getStrategyKey();
            if (strategyKey == null){
                throw new IllegalArgumentException("Strategy key is mandatory");
            }
            return getCommandHandlerWithStrategy(commandProvider, strategyKey);
        } else {
            return getDefaultCommandHandler(commandProvider);
        }
    }

    @SuppressWarnings("unchecked")
    private <R, C extends Command<R>> CommandHandler<R, C> getCommandHandlerWithStrategy(
            CommandProvider<CommandHandler<?, ?>> commandProvider, String strategyKey) {
        return (CommandHandler<R, C>) commandProvider.get(strategyKey);
    }

    @SuppressWarnings("unchecked")
    private <R, C extends Command<R>> CommandHandler<R, C> getDefaultCommandHandler(
            CommandProvider<CommandHandler<?, ?>> commandProvider) {
        return (CommandHandler<R, C>) commandProvider.get();
    }


    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        commandProviderMap.clear();
        initializeCommandProviders(event.getApplicationContext());
    }


    private void initializeCommandProviders(ApplicationContext applicationContext) {
        Map<Class<? extends Command<?>>, List<Class<CommandHandler<?, ?>>>> commandHandlersMap = new HashMap<>();
        processCommandProviders(applicationContext, commandHandlersMap);
        validateCommandHandlers(commandHandlersMap);

        commandHandlersMap.forEach((commandType, handlerClasses) -> {
            CommandProvider<CommandHandler<?, ?>> commandHandlerProvider = new CommandProvider<>(
                    applicationContext);

            if (handlerClasses.size() > 1) {
                handleMultipleHandlers(commandType, handlerClasses, commandHandlerProvider);
            } else {
                handleSingleHandler(handlerClasses, commandHandlerProvider);
            }

            commandProviderMap.put(commandType, commandHandlerProvider);
        });
    }

    private void handleMultipleHandlers(Class<? extends Command<?>> commandType,
            List<Class<CommandHandler<?, ?>>> handlerClasses,
            CommandProvider<CommandHandler<?, ?>> commandHandlerProvider) {
        if (strategyKeyProvider == null) {
            String message = String.format(
                    "A bean is already registered for the argument type: %s. "
                            + "Enable the Strategy feature to allow multiple beans for this type.",
                    commandType.getSimpleName());
            checkUniqueBean(commandType, message);
        } else {
            Map<String, Class<CommandHandler<?, ?>>> strategyTypeMap = commandHandlerProvider.getStrategyTypeMap();
            handlerClasses.forEach(handlerClass -> {
                StrategyKey annotation = handlerClass.getAnnotation(StrategyKey.class);
                if (annotation != null) {
                    strategyTypeMap.put(annotation.value(), handlerClass);
                }
            });
            commandHandlerProvider.setStrategy(true);
        }
    }

    private void handleSingleHandler(List<Class<CommandHandler<?, ?>>> handlerClasses,
            CommandProvider<CommandHandler<?, ?>> commandHandlerProvider) {
        commandHandlerProvider.setStrategy(false);
        if (!handlerClasses.isEmpty()) {
            commandHandlerProvider.setType(handlerClasses.get(0));
        }
    }


    private void processCommandProviders(ApplicationContext applicationContext,
            Map<Class<? extends Command<?>>, List<Class<CommandHandler<?, ?>>>> commandHandlersMap) {
        String[] beanNames = applicationContext.getBeanNamesForType(CommandHandler.class);
        for (String beanName : beanNames) {
            registerCommandProvider(applicationContext, beanName, commandHandlersMap);
        }
    }

    @SuppressWarnings("unchecked")
    private void registerCommandProvider(ApplicationContext applicationContext, String beanName,
            Map<Class<? extends Command<?>>, List<Class<CommandHandler<?, ?>>>> commandHandlersMap) {
        Class<CommandHandler<?, ?>> handlerClass = (Class<CommandHandler<?, ?>>) applicationContext.getType(
                beanName);
        if (handlerClass == null) {
            throw new IllegalStateException(
                    "Handler class for bean %s is null.".formatted(beanName));
        }

        Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handlerClass,
                CommandHandler.class);
        if (generics == null || generics.length != 2) {
            throw new IllegalStateException(
                    "Handler class %s does not have valid parameters.".formatted(
                            handlerClass.getSimpleName()));
        }

        Class<? extends Command<?>> commandType = (Class<? extends Command<?>>) generics[1];

        commandHandlersMap.computeIfAbsent(commandType, k -> new ArrayList<>()).add(handlerClass);
/*
        StrategyProvider strategyProvider = new StrategyProvider(commandType);

        CommandProvider<?> commandProvider = createCommandProvider(applicationContext, handlerClass);
        if (strategyKeyProvider != null) {
            String message = ("A bean is already registered for the argument type: %s."
                    + " Enable the Strategy feature to allow multiple beans for this type.")
                    .formatted(strategyProvider.arg().getSimpleName());
            checkUniqueBean(strategyProvider, message);
        } else {
            handleStrategyKeyAnnotation(handlerClass, strategyProvider);
        }

        commandProviderMap.put(commandType, commandProvider);

        a*/
    }


    private void checkUniqueBean(Class<? extends Command<?>> commandProvider, String message) {
        if (commandProviderMap.containsKey(commandProvider)) {
            throw new IllegalStateException(message);
        }
    }


    private void validateCommandHandlers(Map<Class<? extends Command<?>>,
            List<Class<CommandHandler<?, ?>>>> commandHandlersMap) {

        Set<String> keys = new HashSet<>();

        for (Map.Entry<Class<? extends Command<?>>, List<Class<CommandHandler<?, ?>>>> entry :
                commandHandlersMap.entrySet()) {
            Class<? extends Command<?>> commandType = entry.getKey();
            List<Class<CommandHandler<?, ?>>> handlers = entry.getValue();

            if (handlers.size() > 1) {
                List<Class<CommandHandler<?, ?>>> nonAnnotatedHandlers = getNonAnnotatedHandlers(
                        handlers);
                if (!nonAnnotatedHandlers.isEmpty()) {
                    throw new IllegalStateException("Multiple handlers found for command type "
                            + commandType.getSimpleName()
                            + ", but not all are annotated with @StrategyKey. Non-annotated handlers: "
                            + nonAnnotatedHandlers);
                }
            }

            for (Class<?> handler : handlers) {
                if (handler.isAnnotationPresent(StrategyKey.class)) {
                    StrategyKey strategyKey = handler.getAnnotation(StrategyKey.class);
                    if (!keys.add(strategyKey.value())) {
                        throw new IllegalStateException(
                                "Duplicate @StrategyKey value '" + strategyKey.value()
                                        + "' found in handler " + handler.getSimpleName());
                    }
                }
            }

        }

    }

    private List<Class<CommandHandler<?, ?>>> getNonAnnotatedHandlers(
            List<Class<CommandHandler<?, ?>>> handlers) {

        List<Class<CommandHandler<?, ?>>> nonAnnotatedHandlers = new ArrayList<>();
        for (Class<CommandHandler<?, ?>> handler : handlers) {
            if (!handler.isAnnotationPresent(StrategyKey.class)) {
                nonAnnotatedHandlers.add(handler);
            }
        }
        return nonAnnotatedHandlers;
    }

}
