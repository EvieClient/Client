package com.evieclient.examples

import com.evieclient.Evie
import com.evieclient.events.bus.EventSubscriber
import com.evieclient.events.impl.client.ChatReceivedEvent
import com.evieclient.modules.Category
import com.evieclient.modules.Module

class ExampleModule : Module("ExampleModule", "Example Module", Category.UTIL, true) {
    @EventSubscriber
    fun onChat(event: ChatReceivedEvent) {
        Evie.log("RECEIVED CHAT! : " + event.chatComponent.unformattedText)
    }
}