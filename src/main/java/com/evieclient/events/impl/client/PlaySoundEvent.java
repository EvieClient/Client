package com.evieclient.events.impl.client;

import com.evieclient.events.CancelableEvent;
import lombok.Getter;
import net.minecraft.client.audio.ISound;

/**
 * Fired sound is played on client.
 *
 * @since 1.0.0
 **/
public class PlaySoundEvent extends CancelableEvent {

    @Getter
    private final ISound sound;

    /**
     * @param sound sound played
     **/
    public PlaySoundEvent(ISound sound) {
        this.sound = sound;
    }
}
