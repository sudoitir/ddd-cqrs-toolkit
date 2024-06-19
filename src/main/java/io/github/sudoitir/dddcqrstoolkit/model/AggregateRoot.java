package io.github.sudoitir.dddcqrstoolkit.model;


import io.github.sudoitir.dddcqrstoolkit.valueobject.Version;
import org.springframework.data.domain.AbstractAggregateRoot;

public class AggregateRoot<A extends AggregateRoot<A>> extends AbstractAggregateRoot<A> implements
        Aggregate {

    private Version version;

    public Version getVersion() {
        return version;
    }

    public void setVersion(final Version version) {
        this.version = version;
    }
}
