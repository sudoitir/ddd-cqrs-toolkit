package io.github.sudoitir.dddcqrstoolkit.event;

import io.github.sudoitir.dddcqrstoolkit.valueobject.ULID;
import java.time.Instant;
import org.springframework.context.ApplicationEvent;

public abstract class DomainEvent extends ApplicationEvent {

    protected DomainEvent(final Object source) {
        super(source);
    }

    public abstract ULID getEventId();

    public abstract ULID getAggregateId();

    public abstract Instant getWhen();
}
