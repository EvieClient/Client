

package com.evieclient.events.impl.client.input;

import com.evieclient.events.Event;
import lombok.Getter;

/** Fired when key is released.
 * @since 1.0.0 **/
public class KeyReleasedEvent extends Event {

    @Getter private final boolean repeatedKey;
    @Getter private final int keyCode;

    /** @param repeatedKey is key event same as last key event
     * @param keyCode int code of key **/
    public KeyReleasedEvent(boolean repeatedKey, int keyCode) {
        this.repeatedKey = repeatedKey;
        this.keyCode = keyCode;
    }
}
