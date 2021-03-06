package com.evieclient.events.impl.client;

import net.minecraft.util.IChatComponent;

/**
 * Fired when the Action Bar is updated.
 *
 * @since 1.0.0
 **/
public class ActionBarEvent extends ChatReceivedEvent {

    /**
     * @param chatComponent action bar text
     **/
    public ActionBarEvent(IChatComponent chatComponent) {
        super(chatComponent);
    }
}
