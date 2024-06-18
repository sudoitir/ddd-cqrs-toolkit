package io.github.sudoitir.dddcqrstoolkit.cqs;

public interface QueryBus {

    <R, Q extends Query<R>> R executeQuery(Q query);
}
