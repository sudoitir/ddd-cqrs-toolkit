package io.github.sudoitir.dddcqrstoolkit.event;

import io.github.sudoitir.dddcqrstoolkit.ulid.ULID;
import java.time.Instant;
import org.springframework.context.ApplicationEvent;

public abstract class DomainEvent<E> extends ApplicationEvent {

    protected DomainEvent(final E source) {
        super(source);
    }

    public abstract ULID getEventId();

    public abstract ULID getAggregateId();

    public abstract Instant getWhen();

    @Override
    @SuppressWarnings("unchecked")
    public E getSource() {
        return (E) super.getSource();
    }
}
