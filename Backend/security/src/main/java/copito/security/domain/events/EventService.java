package copito.security.domain.events;

import copito.security.domain.model.events.Event;

public interface EventService<T extends Event> {
    Boolean execute(T event);
}
