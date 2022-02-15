package com.evieclient.events.impl.client;

import com.evieclient.Evie;
import com.evieclient.events.CancelableEvent;
import lombok.Getter;
import lombok.Setter;

/**
 * Fired when the client sends a message to the server.
 *
 * @since 1.0.0
 **/
public class PlayerChatEvent extends CancelableEvent {

    @Getter
    @Setter
    public String message;

    /**
     * @param chatMessage message being sent to the server
     **/
    public PlayerChatEvent(String chatMessage) {
        message = chatMessage;
    }
}
