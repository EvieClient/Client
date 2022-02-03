

package com.evieclient.hud.framework;

import lombok.Getter;

/**
 * Panels holds and scales all object.
 * <p>Location and scale can also be adjusted via this panel.</p>
 * @since 1.0.0 **/
public class Panel {


    @Getter private int xPosition;
    @Getter private int yPosition;

    @Getter private int scale;
    @Getter private int scaleFactor;

    public final int scale(int number) { return this.scale * number / scaleFactor; }
}
