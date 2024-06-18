package io.github.sudoitir.dddcqrstoolkit.cqs;

@SuppressWarnings("unchecked")
public class SpringQueryBus implements QueryBus {

    private final QueryRegistry queryRegistry;

    public SpringQueryBus(QueryRegistry queryRegistry) {
        this.queryRegistry = queryRegistry;
    }

    @Override
    public <R, Q extends Query<R>> R executeQuery(Q query) {
        QueryHandler<R, Q> queryHandler = (QueryHandler<R, Q>) queryRegistry.getQuery(
                query.getClass());
        return queryHandler.handle(query);
    }
}
