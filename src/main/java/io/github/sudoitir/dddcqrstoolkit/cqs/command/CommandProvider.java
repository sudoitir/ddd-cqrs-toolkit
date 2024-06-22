package io.github.sudoitir.dddcqrstoolkit.cqs.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class CommandProvider<H extends CommandHandler<?, ?>> {

    private final ApplicationContext applicationContext;
    private final Map<String, Class<H>> strategyTypeMap = new HashMap<>();
    private Class<H> type;
    private Boolean isStrategy;

    CommandProvider(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Map<String, Class<H>> getStrategyTypeMap() {
        return strategyTypeMap;
    }

    public void setType(final Class<H> type) {
        this.type = type;
    }

    public void setStrategy(final Boolean strategy) {
        isStrategy = strategy;
    }

    public H get() {
        return applicationContext.getBean(type);
    }

    @Nullable
    public H get(@NonNull String strategyKey) {
        if (isStrategy && strategyTypeMap.containsKey(strategyKey)) {
            return applicationContext.getBean(strategyTypeMap.get(strategyKey));
        } else {
            throw new IllegalArgumentException("Strategy key not found!");
        }
    }

    public Boolean isStrategy() {
        return isStrategy;
    }

    @Override
    public int hashCode() {
        int result = strategyTypeMap.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (isStrategy != null ? isStrategy.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommandProvider<?> that = (CommandProvider<?>) o;

        if (!strategyTypeMap.equals(that.strategyTypeMap)) {
            return false;
        }
        if (!Objects.equals(type, that.type)) {
            return false;
        }
        return Objects.equals(isStrategy, that.isStrategy);
    }

    @Override
    public String toString() {
        return "CommandProvider{" + "strategyTypeMap=" + strategyTypeMap
                + ", type=" + type
                + ", isStrategy=" + isStrategy
                + '}';
    }
}
