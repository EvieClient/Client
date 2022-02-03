package com.evieclient.events;

import lombok.Getter;

/** Cancelable event class.
 * <p>Events that extend this class will be cancelable.
 * This means that if {@code cancel} is called,
 * the action that triggered the event will not happen.</p>
 * @since 1.0.0 **/
public class CancelableEvent extends Event {
    @Getter private boolean canceled = false;

    /** Cancels the event. **/
    public void cancel() {
        canceled = true;
    }
}
