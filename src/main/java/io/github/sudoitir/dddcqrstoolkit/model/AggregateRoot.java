package io.github.sudoitir.dddcqrstoolkit.model;


import org.springframework.data.domain.AbstractAggregateRoot;

public class AggregateRoot<A extends AggregateRoot<A>> extends AbstractAggregateRoot<A> implements
        Aggregate {

}
