package io.github.sudoitir.dddcqrstoolkit.cqs;

public interface QueryHandler<R, C extends Query<R>> {

    R handle(C query);
}
