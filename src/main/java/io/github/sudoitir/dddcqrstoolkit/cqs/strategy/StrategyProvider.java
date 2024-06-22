package io.github.sudoitir.dddcqrstoolkit.cqs.strategy;

import java.util.Objects;

public final class StrategyProvider {

    private final Class<?> arg;
    private String strategyKey;

    public StrategyProvider(Class<?> arg) {
        this.arg = arg;
    }


    public Class<?> arg() {
        return arg;
    }

    public String strategyKey() {
        return strategyKey;
    }

    public void setStrategyKey(final String strategyKey) {
        this.strategyKey = strategyKey;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StrategyProvider that = (StrategyProvider) o;

        if (!arg.equals(that.arg)) {
            return false;
        }
        return Objects.equals(strategyKey, that.strategyKey);
    }

    @Override
    public int hashCode() {
        int result = arg.hashCode();
        result = 31 * result + (strategyKey != null ? strategyKey.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StrategyProvider[" +
                "arg=" + arg + ", " +
                "strategyKey=" + strategyKey + ']';
    }

}
