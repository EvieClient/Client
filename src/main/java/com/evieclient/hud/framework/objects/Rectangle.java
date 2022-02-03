

package com.evieclient.hud.framework.objects;

import com.evieclient.Evie;
import com.evieclient.hud.framework.Panel;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

import java.awt.*;

/** Draws a rectangle on screen.
 * @since 1.0.0 **/
public class Rectangle extends GuiElement{

    // Position of rectangle with either 4 points or 2 w/ width and height.
    @Getter @Setter private int x1;
    @Getter @Setter private int y1;
    @Getter @Setter private int x2 = -1;
    @Getter @Setter private int y2 = -1;
    // Color of rectangle.
    @Getter @Setter private Color color;
    @Getter @Setter private Color outlineColor;
    // Outline width of rectangle.
    @Getter @Setter private int outlineLeft = 0;
    @Getter @Setter private int outlineTop = 0;
    @Getter @Setter private int outlineRight = 0;
    @Getter @Setter private int outlineBottom = 0;
    // holds value for each corner rectangle.
    @Getter @Setter private int topLeftCorner = 0;
    @Getter @Setter private int topRightCorner = 0;
    @Getter @Setter private int bottomLeftCorner = 0;
    @Getter @Setter private int bottomRightCorner = 0;
    // Gaps to adjust corners
    @Getter private float xGap = 1f;
    @Getter private float yGap = 1f;

    /** @param name name of the rectangle **/
    public Rectangle (String name) { super(name); }

    /** change location of rectangle
     * @param x1 first x position
     * @param y1 first y position **/
    public Rectangle position(int x1, int y1) {
        this.x1 = x1;
        this.y1 = y1;
        return this;
    }

    /** change location of rectangle
     * @param x1 first x position
     * @param y1 first y position
     * @param x2 second x position
     * @param y2 second y position **/
    public Rectangle position(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y1 = y2;
        return this;
    }

    /** Set the size of the rectangle.
     * @param width width of rectangle.
     * @param height height of rectangle.
     * @return this rectangle **/
    public Rectangle size (int width, int height) {
        if (this.x2 != -1) { Evie.error("[framework-" + this.name + "] Object controlled by points, size has been ignored!"); return this; }
        this.x2 = x1 + width;
        this.y2 = y1 + height;
        return this;
    }

    /** Round the rectangles corners
     * @param amount amount to round.
     * @return this rectangle **/
    public Rectangle round(int amount) {
        this.topLeftCorner = amount;
        this.topRightCorner = amount;
        this.bottomLeftCorner = amount;
        this.bottomRightCorner = amount;
        return this;
    }

    /** round corners of rectangle per corner.
     * @param topLeft top left corner
     * @param topRight top right corner
     * @param bottomLeft bottom left corner
     * @param bottomRight bottom right corner
     * @return this rectangle*/
    public Rectangle corners (int topLeft, int topRight, int bottomLeft, int bottomRight) {
        this.topLeftCorner = topLeft;
        this.topRightCorner = topRight;
        this.bottomLeftCorner = bottomLeft;
        this.bottomRightCorner = bottomRight;
        return this;
    }

    /** Makes corners strength by offset. set as percent **/
    public Rectangle cornerOffsets(float xGap, float yGap) {
        this.xGap = xGap;
        this.yGap = yGap;
        return this;
    }

    public Rectangle outline(int amount) {
        this.outlineLeft = amount;
        this.outlineRight = amount;
        this.outlineTop = amount;
        this.outlineBottom = amount;
        return this;
    }

    public Rectangle outlines(int outlineLeft, int outlineTop, int outlineRight, int outlineBottom) {
        this.outlineLeft = outlineLeft;
        this.outlineRight = outlineRight;
        this.outlineTop = outlineTop;
        this.outlineBottom = outlineBottom;
        return this;
    }

    public Rectangle color(Color color) {
        this.color = color;
        return this;
    }

    public Rectangle outlineColor(Color color) {
        this.outlineColor = color;
        return this;
    }

    /** Try to fix any errors caused when creating Rectangle **/
    @Override public Rectangle build() {
        if (this.color == null) { this.color = Color.BLACK;
            Evie.error("[framework-" + this.name + "] Rectangle had no color and has been defaulted to BLACK!");
        } if (this.outlineColor == null && (this.outlineBottom > 0 || this.outlineTop > 0 || this.outlineLeft > 0 || this.outlineRight > 0)) { this.outlineColor = Color.darkGray;
            Evie.error("[framework-" + this.name + "] Rectangle had no outline color and has been defaulted to DARK-GRAY!");
        } if (this.x2 == -1) { this.x2 = this.x1 + 10; this.y2 = this.y1 + 10;
            Evie.error("[framework-" + this.name + "] Rectangle has no size or secondary position and has be defaulted to 10:10");
        } if (this.topLeftCorner > ((this.y2 - this.y1) / 2) || this.topRightCorner > ((this.y2 - this.y1) / 2) || this.bottomRightCorner > ((this.y2 - this.y1) / 2) || this.bottomLeftCorner > ((this.y2 - this.y1) / 2)) {
            Evie.error("[framework-" + this.name + "] Rectangle corner was to large and have been reduced!");
        } if (this.topLeftCorner > ((this.y2 - this.y1) / 2))  {  this.topLeftCorner = ((this.y2 - this.y1) / 2);
        } if (this.topRightCorner > ((this.y2 - this.y1) / 2)) {  this.topRightCorner = ((this.y2 - this.y1) / 2);
        } if (this.bottomRightCorner > ((this.y2 - this.y1) / 2)) {  this.bottomRightCorner = ((this.y2 - this.y1) / 2);
        } if (this.bottomLeftCorner > ((this.y2 - this.y1) / 2)) {  this.bottomLeftCorner = ((this.y2 - this.y1) / 2);
        } if (this.xGap > 1f || this.yGap > 1f) {
            Evie.error("[framework-" + this.name + "] Rectangle gap offsets was to large and have been reduced!");
        } if (this.xGap > 1f) this.xGap = 1f;
        if (this.yGap > 1f) this.yGap = 1f;
        if (this.x1 == -1 || this.y1 == -1) {
        Evie.error("[framework-" + this.name + "] Rectangle has no position and could not be created!");
        return null;
        } return this;
    }

    /** Generates the GL attributes used for a rectangle. **/
    @Override public final void openGLAttributes() {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    }

    /** Closes the GL attributes used for a rectangle. **/
    @Override public final void closeGLAttributes() {
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    @Override public void draw (Panel panel) {
        WorldRenderer worldrenderer = Tessellator.getInstance().getWorldRenderer();
        int x1, y1, x2, y2;
        x1 = panel.getXPosition() + panel.scale(this.x1);
        y1 = panel.getYPosition() + panel.scale(this.y1);
        x2 = panel.getXPosition() + panel.scale(this.x2);
        y2 = panel.getYPosition() + panel.scale(this.y2);

        int longerLeftGap = this.topLeftCorner > this.bottomLeftCorner ? this.topLeftCorner : this.bottomLeftCorner;
        int longerRightGap = this.topRightCorner > this.bottomRightCorner ? this.topRightCorner  : this.bottomRightCorner;

    }
}
