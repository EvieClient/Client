package com.evieclient.events.impl.client

import com.evieclient.Evie
import net.minecraft.util.IChatComponent
import com.evieclient.events.CancelableEvent
import lombok.Getter

/**
 * Fired when the client receives a chat packet, and it is not an action bar packet.
 *
 * If you want the action bar, see the [ActionBarEvent] class.
 *
 * @since 1.0.0
 */
open class ChatReceivedEvent(@field:Getter val chatComponent: IChatComponent) : CancelableEvent() {
    @Getter
    val message: String = chatComponent.unformattedText
}