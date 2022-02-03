

package com.evieclient.events.impl.world;

import com.evieclient.events.Event;
import lombok.Getter;
import net.minecraft.client.multiplayer.WorldClient;

/** Fired when player world is changed.
 * @since 1.0.0 **/
public class LoadWorldEvent extends Event {

    @Getter private final WorldClient worldClient;

    /** @param worldClient new world client loaded */
    public LoadWorldEvent(WorldClient worldClient) {
        this.worldClient = worldClient;
    }
}
