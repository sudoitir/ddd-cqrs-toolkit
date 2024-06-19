package io.github.sudoitir.dddcqrstoolkit.cqs.command;

public class SpringCommandBus implements CommandBus {

    private final CommandRegistry commandRegistry;

    public SpringCommandBus(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R, C extends Command<R>> R executeCommand(C command) {
        CommandHandler<R, C> commandHandler = commandRegistry.getCommandHandler(command.getClass());
        return commandHandler.handle(command);
    }

}
