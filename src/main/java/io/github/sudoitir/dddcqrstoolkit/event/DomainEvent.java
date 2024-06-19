package io.github.sudoitir.dddcqrstoolkit.event;

import io.github.sudoitir.dddcqrstoolkit.ulid.ULID;
import org.springframework.context.ApplicationEvent;

import java.time.Instant;

public abstract class DomainEvent extends ApplicationEvent {

    protected DomainEvent(final Object source) {
        super(source);
    }

    public abstract ULID getEventId();

    public abstract ULID getAggregateId();

    public abstract Instant getWhen();
}
