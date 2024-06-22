package io.github.sudoitir.dddcqrstoolkit.cqs.strategy;

import org.springframework.lang.Nullable;

public interface StrategyKeyProvider {

    @Nullable
    String getStrategyKey();

}
