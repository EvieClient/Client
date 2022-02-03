

package com.evieclient.events.bus;

import lombok.Getter;

/** Enum for storing the order in which event listeners are fired.
 * <p>A listener with a {@code HIGH} priority will be fired
 * before a listener with a {@code NORMAL} priority.</p>
 * <p>The priority of a listener is set in the {@link EventSubscriber},
 * but it can be changed at any time with the {@code setPriorityOf}
 * method in {@link EventBus}.</p>
 * @since 1.0.0 **/
public enum Priority {

    HIGH(0),
    NORMAL(1),
    LOW(2);

    @Getter private final int id;
    Priority(int id) {
        this.id = id;
    }
}