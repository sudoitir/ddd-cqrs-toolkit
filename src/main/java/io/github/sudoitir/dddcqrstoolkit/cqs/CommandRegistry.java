package io.github.sudoitir.dddcqrstoolkit.cqs;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;


/**
 * Registry holds the mapping between a command and its handler. The registry should always be
 * injected, by the spring framework.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class CommandRegistry {

    private final Map<Class<? extends Command>, CommandProvider> commandProviderMap = new HashMap<>();

    public CommandRegistry(ApplicationContext applicationContext) {
        String[] names = applicationContext.getBeanNamesForType(CommandHandler.class);
        for (String name : names) {
            registerCommand(applicationContext, name);
        }
    }

    private void registerCommand(ApplicationContext applicationContext, String name) {
        Class<CommandHandler<?, ?>> handlerClass = (Class<CommandHandler<?, ?>>) applicationContext
                .getType(name);
        Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handlerClass,
                CommandHandler.class);
        Class<? extends Command> commandType = (Class<? extends Command>) generics[1];
        commandProviderMap.put(commandType, new CommandProvider(applicationContext, handlerClass));
    }

    <R, C extends Command<R>> CommandHandler<R, C> getCmd(Class<C> commandClass) {
        return commandProviderMap.get(commandClass).get();
    }

}
