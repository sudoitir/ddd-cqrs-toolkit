package io.github.sudoitir.dddcqrstoolkit.cqs.query;

import org.springframework.stereotype.Component;

@Component
public class SpringQueryBus implements QueryBus {

    private final QueryRegistry queryRegistry;

    public SpringQueryBus(QueryRegistry queryRegistry) {
        this.queryRegistry = queryRegistry;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R, Q extends Query<R>> R executeQuery(Q query) {
        QueryHandler<R, Q> queryHandler = queryRegistry.getQueryHandler(query.getClass());
        return queryHandler.handle(query);
    }
}
