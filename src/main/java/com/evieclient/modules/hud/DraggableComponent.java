package com.evieclient.modules.hud;

import com.evieclient.utils.shader.GLRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;
import java.awt.Color;

public class DraggableComponent {

    private final int width;
    private final int height;
    private int x;
    private int y;
    private int color;
    private int lastX;
    private int lastY;

    private boolean dragging;

    public DraggableComponent(int x, int y, int width, int height, int color) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getxPosition() {
        return x;
    }

    public void setxPosition(int x) {
        this.x = x;
    }

    public int getyPosition() {
        return y;
    }

    public void setyPosition(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void draw(int mouseX, int mouseY) {
        draggingFix(mouseX, mouseY);
        Gui.drawRect(this.getxPosition(), this.getyPosition(), this.getxPosition() + this.getWidth(), this.getyPosition() + this.getHeight(), this.getColor());
        boolean mouseOverX = (mouseX >= this.getxPosition() && mouseX <= this.getxPosition() + this.getWidth());
        boolean mouseOverY = (mouseY >= this.getyPosition() && mouseY <= this.getyPosition() + this.getHeight());
        if (mouseOverX && mouseOverY) {
            if (Mouse.isButtonDown(0)) {
                if (!this.dragging) {
                    this.lastX = x - mouseX;
                    this.lastY = y - mouseY;
                    this.dragging = true;
                }
            }
        }
    }

    private void draggingFix(int mouseX, int mouseY) {
        if (this.dragging) {
            this.x = mouseX + this.lastX;
            this.y = mouseY + this.lastY;
            if (!Mouse.isButtonDown(0)) this.dragging = false;

            if (!Mouse.isButtonDown(0)) {
                int gridSize = 10;
                int x = this.getxPosition();
                int y = this.getyPosition();
                int xSnap = x % gridSize;
                int ySnap = y % gridSize;
                if (xSnap > gridSize / 2) x += gridSize - xSnap;
                else x -= xSnap;
                if (ySnap > gridSize / 2) y += gridSize - ySnap;
                else y -= ySnap;
                this.setxPosition(x);
                this.setyPosition(y);
            }
        }
    }
}