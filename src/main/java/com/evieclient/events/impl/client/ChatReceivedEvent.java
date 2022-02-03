

package com.evieclient.events.impl.client;

import com.evieclient.events.CancelableEvent;
import lombok.Getter;
import net.minecraft.util.IChatComponent;

/** Fired when the client receives a chat packet and it is not an action bar packet.
 * <p>If you want the action bar, see the {@link ActionBarEvent} class.</p>
 * @since 1.0.0 **/
public class ChatReceivedEvent extends CancelableEvent {

    @Getter private final IChatComponent chatComponent;

    /** @param chatComponent chat component from the packet **/
    public ChatReceivedEvent(IChatComponent chatComponent) {
        this.chatComponent = chatComponent;
    }
}
