package io.github.sudoitir.dddcqrstoolkit.cqs;

@SuppressWarnings("unchecked")
public class SpringCommandBus implements CommandBus {

    private final CommandRegistry commandRegistry;

    public SpringCommandBus(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }

    @Override
    public <R, C extends Command<R>> R executeCommand(C command) {
        CommandHandler<R, C> commandHandler = (CommandHandler<R, C>) commandRegistry.getCmd(
                command.getClass());
        return commandHandler.handle(command);
    }

}
