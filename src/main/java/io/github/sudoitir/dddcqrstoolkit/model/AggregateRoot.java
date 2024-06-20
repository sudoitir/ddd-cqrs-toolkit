package io.github.sudoitir.dddcqrstoolkit.model;


import io.github.sudoitir.dddcqrstoolkit.event.DomainEvent;
import io.github.sudoitir.dddcqrstoolkit.valueobject.Version;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AggregateRoot<A extends AggregateRoot<A>> implements Aggregate {

    private final transient @Transient List<DomainEvent<?>> domainEvents = new ArrayList<>();

    private Version version;

    public Version getVersion() {
        return version;
    }

    public void setVersion(final Version version) {
        this.version = version;
    }


    /**
     * Registers the given event object for publication on a call to a Spring Data repository's save or delete methods.
     *
     * @param event must not be {@literal null}.
     *
     * @return the event that has been added.
     *
     * @see #andEvent(DomainEvent)
     */
    protected <E extends DomainEvent<?>> E registerEvent(@NonNull E event) {

        Assert.notNull(event, "Domain event must not be null");

        this.domainEvents.add(event);
        return event;
    }

    /**
     * Clears all domain events currently held. Usually invoked by the infrastructure in place in Spring Data
     * repositories.
     */
    @AfterDomainEventPublication
    protected void clearDomainEvents() {
        this.domainEvents.clear();
    }

    /**
     * All domain events currently captured by the aggregate.
     */
    @DomainEvents
    protected Collection<DomainEvent<?>> domainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    /**
     * Adds all events contained in the given aggregate to the current one.
     *
     * @param aggregate must not be {@literal null}.
     *
     * @return the aggregate
     */
    @SuppressWarnings ("unchecked")
    protected final A andEventsFrom(@NonNull A aggregate) {

        Assert.notNull(aggregate, "Aggregate must not be null");

        this.domainEvents.addAll(aggregate.domainEvents());

        return (A) this;
    }

    /**
     * Adds the given event to the aggregate for later publication when calling a Spring Data repository's save or
     * delete method. Does the same as {@link #registerEvent(DomainEvent)} but returns the aggregate instead of the
     * event.
     *
     * @param event must not be {@literal null}.
     *
     * @return the aggregate
     *
     * @see #registerEvent(DomainEvent)
     */
    @SuppressWarnings ("unchecked")
    protected final <E extends DomainEvent<?>> A andEvent(@NonNull E event) {

        registerEvent(event);

        return (A) this;
    }

}
