package io.github.sudoitir.dddcqrstoolkit.cqs.strategy;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.Nullable;

public class HeaderBasedStrategyKeyProvider implements StrategyKeyProvider {

    private static final String STRATEGY_KEY_HEADER = "X-Strategy-Key";

    private final HttpServletRequest request;

    public HeaderBasedStrategyKeyProvider(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    @Nullable
    public String getStrategyKey() {
        return request.getHeader(getStrategyKeyHeader());
    }

    public String getStrategyKeyHeader() {
        return STRATEGY_KEY_HEADER;
    }
}
