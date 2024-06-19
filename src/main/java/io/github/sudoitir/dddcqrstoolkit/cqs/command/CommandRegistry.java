package io.github.sudoitir.dddcqrstoolkit.cqs.command;

import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;

import java.util.HashMap;
import java.util.Map;


/**
 * Registry holds the mapping between a command and its handler. The registry should always be
 * injected, by the spring framework.
 */
public class CommandRegistry {

    private final Map<Class<? extends Command<?>>, CommandProvider<?>> commandProviderMap = new HashMap<>();

    public CommandRegistry(ApplicationContext applicationContext) {
        initializeCommandProviders(applicationContext);
    }

    @SuppressWarnings("unchecked")
    public <R, C extends Command<R>> CommandHandler<R, C> getCommandHandler(Class<C> commandClass) {
        CommandProvider<?> provider = commandProviderMap.get(commandClass);
        if (provider == null) {
            throw new IllegalArgumentException(
                    "No CommandHandler found for command class: " + commandClass);
        }
        return (CommandHandler<R, C>) provider.get();
    }

    private void initializeCommandProviders(ApplicationContext applicationContext) {
        String[] beanNames = applicationContext.getBeanNamesForType(CommandHandler.class);
        for (String beanName : beanNames) {
            registerCommandProvider(applicationContext, beanName);
        }
    }

    @SuppressWarnings("unchecked")
    private void registerCommandProvider(ApplicationContext applicationContext, String beanName) {
        Class<?> handlerClass = applicationContext.getType(beanName);
        if (handlerClass != null) {
            Class<?>[] generics = GenericTypeResolver
                    .resolveTypeArguments(handlerClass, CommandHandler.class);
            if (generics != null && generics.length == 2) {
                Class<? extends Command<?>> commandType = (Class<? extends Command<?>>) generics[1];
                commandProviderMap.put(commandType, new CommandProvider<>(applicationContext,
                        (Class<? extends CommandHandler<?, ?>>) handlerClass));
            }
        }
    }
}
