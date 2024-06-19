package io.github.sudoitir.dddcqrstoolkit.cqs.setup;

import io.github.sudoitir.dddcqrstoolkit.cqs.query.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExceptionalGetProductQueryHandler implements QueryHandler<Void, VoidProductQuery> {



    @SuppressWarnings ("ConstantValue")
    @Override
    public Void handle(VoidProductQuery query) {
        if (true){
            throw new IllegalArgumentException("Test exception");
        }
        return null;
    }

}
