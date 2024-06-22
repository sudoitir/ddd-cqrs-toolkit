package io.github.sudoitir.dddcqrstoolkit.event;


import io.vavr.collection.List;

public interface DomainEvents {

    default void publish(List<DomainEvent> events) {
        events.forEach(this::publish);
    }

    void publish(DomainEvent event);
}
