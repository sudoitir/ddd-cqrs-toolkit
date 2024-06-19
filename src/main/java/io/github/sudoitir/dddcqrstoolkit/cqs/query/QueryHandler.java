package io.github.sudoitir.dddcqrstoolkit.cqs.query;

public interface QueryHandler<R, Q extends Query<R>> {

    R handle(Q query);
}
