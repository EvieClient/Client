

package com.evieclient.events.bus;

import com.evieclient.Evie;
import com.evieclient.events.Event;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

/** Internal data class for storing event listener data.
 * @author Nora Cos | Nora#0001
 * @since 1.0.0 **/
public class EventListener {

    @Getter
    private final Object instance;
    @Getter @Setter
    private Method method;
    @Getter private Priority eventPriority;

    public EventListener(Object listener, Method method, Priority eventPriority) {
        instance = listener;
        this.method = method;
        this.eventPriority = eventPriority;
    }

    /** Sets the event priority and sorts the event listeners.
     * @param eventPriority new event priority
     * @param classToSort event class **/
    void setEventPriority(Priority eventPriority, Class<? extends Event> classToSort) {
        this.eventPriority = eventPriority;
        if (classToSort != null) {
            Evie.EVENT_BUS.sort(classToSort);
        }
    }
}
