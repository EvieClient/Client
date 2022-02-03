

package com.evieclient.hud.framework.objects;

import com.evieclient.hud.framework.Panel;
import lombok.Getter;

/** Gui element class all objects extend
 * @since 1.0.0 **/
public abstract class GuiElement {

    @Getter public String name;
    public GuiElement (String name) { this.name = name; }

    public abstract void openGLAttributes();
    public abstract void closeGLAttributes();
    public abstract GuiElement build();
    public abstract void draw(Panel panel);
}
