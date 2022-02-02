

package com.evieclient.events.bus;

import com.evieclient.events.Event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotation for all event listener methods.
 * The first parameter in methods annotated with this must be
 * a class that extends {@link Event}.
 * @see EventBus
 * @author Nora Cos | Nora#0001
 * @since 1.0.0 **/
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface EventSubscriber {

    /** Priority of the event.
     * @see Priority
     * @return event priority */
    Priority priority() default Priority.NORMAL;
}