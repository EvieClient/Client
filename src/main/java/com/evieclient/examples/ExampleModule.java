

package com.evieclient.examples;

import com.evieclient.Evie;
import com.evieclient.events.bus.EventSubscriber;
import com.evieclient.modules.Category;
import com.evieclient.modules.Module;
import com.evieclient.events.impl.client.ChatReceivedEvent;

public class ExampleModule extends Module {
    public ExampleModule() { super("ExampleModule", "Example Module", Category.UTIL, true); }

    @EventSubscriber
    public void onChat (ChatReceivedEvent event) {
        Evie.log("RECEIVED CHAT! : " + event.getChatComponent().getUnformattedText());
    }
}
