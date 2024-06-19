package io.github.sudoitir.dddcqrstoolkit.cqs.query;

import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry holds the mapping between a query and its handler. The registry should always be
 * injected, by the spring framework.
 */
public class QueryRegistry {

    private final Map<Class<? extends Query<?>>, QueryProvider<?>> queryProviderMap = new HashMap<>();

    public QueryRegistry(ApplicationContext applicationContext) {
        initializeQueryProviders(applicationContext);
    }

    @SuppressWarnings("unchecked")
    public <R, Q extends Query<R>> QueryHandler<R, Q> getQueryHandler(Class<Q> queryClass) {
        QueryProvider<?> provider = queryProviderMap.get(queryClass);
        if (provider == null) {
            throw new IllegalArgumentException(
                    "No QueryHandler found for query class: " + queryClass);
        }
        return (QueryHandler<R, Q>) provider.get();
    }

    private void initializeQueryProviders(ApplicationContext applicationContext) {
        String[] beanNames = applicationContext.getBeanNamesForType(QueryHandler.class);
        for (String beanName : beanNames) {
            registerQueryProvider(applicationContext, beanName);
        }
    }

    @SuppressWarnings("unchecked")
    private void registerQueryProvider(ApplicationContext applicationContext, String beanName) {
        Class<?> handlerClass = applicationContext.getType(beanName);
        if (handlerClass != null) {
            Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handlerClass,
                    QueryHandler.class);
            if (generics != null && generics.length == 2) {
                Class<? extends Query<?>> queryType = (Class<? extends Query<?>>) generics[1];
                queryProviderMap.put(queryType, new QueryProvider<>(applicationContext,
                        (Class<? extends QueryHandler<?, ?>>) handlerClass));
            }
        }
    }


}
