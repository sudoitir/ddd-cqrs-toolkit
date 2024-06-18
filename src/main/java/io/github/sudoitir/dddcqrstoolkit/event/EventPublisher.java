package io.github.sudoitir.dddcqrstoolkit.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public EventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publish(Event event) {
        eventPublisher.publishEvent(event);
    }
}
