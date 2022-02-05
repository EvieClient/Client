package com.evieclient.events.impl.client.input;

import com.evieclient.events.Event;
import lombok.Getter;

/**
 * Fired when key is pressed.
 *
 * @since 1.0.0
 **/
public class KeyPressedEvent extends Event {

    @Getter
    private final boolean repeatedKey;
    @Getter
    private final int keyCode;

    /**
     * @param repeatedKey is key event same as last key event
     * @param keyCode     int code of key
     **/
    public KeyPressedEvent(boolean repeatedKey, int keyCode) {
        this.repeatedKey = repeatedKey;
        this.keyCode = keyCode;
    }
}
