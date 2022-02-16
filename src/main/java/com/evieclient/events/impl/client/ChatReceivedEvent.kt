package com.evieclient.events.impl.client

import com.evieclient.events.CancelableEvent
import lombok.Getter
import net.minecraft.util.IChatComponent

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