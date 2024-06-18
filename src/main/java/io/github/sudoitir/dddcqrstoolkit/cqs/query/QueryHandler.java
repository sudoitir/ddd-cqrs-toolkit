package io.github.sudoitir.dddcqrstoolkit.cqs.query;

public interface QueryHandler<R, C extends Query<R>> {

    R handle(C query);
}
