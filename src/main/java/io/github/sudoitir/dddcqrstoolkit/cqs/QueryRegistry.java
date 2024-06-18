package io.github.sudoitir.dddcqrstoolkit.cqs;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;

/**
 * Registry holds the mapping between a query and its handler. The registry should always be
 * injected, by the spring framework.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class QueryRegistry {

    private final Map<Class<? extends Query>, QueryProvider> queryProviderMap = new HashMap<>();

    public QueryRegistry(ApplicationContext applicationContext) {
        String[] names = applicationContext.getBeanNamesForType(QueryHandler.class);
        names = applicationContext.getBeanNamesForType(QueryHandler.class);
        for (String name : names) {
            registerQuery(applicationContext, name);
        }
    }

    private void registerQuery(ApplicationContext applicationContext, String name) {
        Class<QueryHandler<?, ?>> handlerClass = (Class<QueryHandler<?, ?>>) applicationContext
                .getType(name);
        Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handlerClass,
                QueryHandler.class);
        Class<? extends Query> queryType = (Class<? extends Query>) generics[1];
        queryProviderMap.put(queryType, new QueryProvider(applicationContext, handlerClass));
    }

    <R, C extends Query<R>> QueryHandler<R, C> getQuery(Class<C> commandClass) {
        return queryProviderMap.get(commandClass).get();
    }
}
