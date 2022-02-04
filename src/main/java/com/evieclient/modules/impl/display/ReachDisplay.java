package com.evieclient.modules.impl.display;

import com.evieclient.Evie;
import com.evieclient.modules.Category;
import com.evieclient.modules.hud.RenderModule;
import net.minecraft.entity.EntityLivingBase;

public class ReachDisplay extends RenderModule {

    EntityLivingBase hit = null;
    double reach;

    public ReachDisplay() {
        super("ReachDisplay", "Displays your reach", Category.DISPLAY, true);
    }

    @Override
    public void renderDummy() {
        fr.drawStringWithShadow("3.00", getX(), getY(), -1);
    }

    @Override
    public void onEnabled(){
        Evie.log("reachdisplay == on");
    }

    @Override
    public void onDisable(){
        Evie.log("reachdisplay == off");
    }


    @Override
    public void render() {
        Evie.log("Rendering reachdisplay");
        hit = (EntityLivingBase) mc.pointedEntity;
        if (hit != null) {
            if (hit.hurtTime > 0) {
                reach = mc.thePlayer.getDistanceToEntity(hit) - 1;
            }
        }
        int n = 3;

        fr.drawStringWithShadow(Double.parseDouble(("" + reach).substring(0, n)) + "", getX(), getY(), -1);

    }

    @Override
    public int getWidth() {

        return fr.getStringWidth("3.00");
    }


    @Override
    public int getHeight() {

        return fr.FONT_HEIGHT;

    }

}
//
//import com.evieclient.Evie;
//import com.evieclient.events.bus.EventSubscriber;
//import com.evieclient.events.impl.client.input.KeyPressedEvent;
//import com.evieclient.modules.Category;
//import com.evieclient.modules.Module;
//import com.evieclient.modules.ModuleManager;
//import com.evieclient.utils.render.FontRenderer;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.Gui;
//import net.minecraft.client.renderer.GlStateManager;
//import net.minecraft.client.settings.KeyBinding;
//import org.lwjgl.input.Keyboard;
//import org.lwjgl.input.Mouse;
//import org.lwjgl.opengl.GL11;
//import org.newdawn.slick.SlickException;
//
//import java.awt.*;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static net.minecraft.client.gui.Gui.drawRect;
//
//public class Keystrokes extends Module {
//    public Keystrokes() throws SlickException, IOException, FontFormatException {
//        super("Keystrokes", "Show off your CPS and Keys.    ", Category.DISPLAY, true, true);
//    }
//
//    public static enum KeystrokesOpt {
//        WASD(Key.W, Key.A, Key.S, Key.D),
//        WASD_MOUSE(Key.W, Key.A, Key.S, Key.D, Key.LMB, Key.RMB),
//        WASD_JUMP(Key.W, Key.A, Key.S, Key.D, Key.Jump1),
//        WASD_JUMP_MOUSE(Key.W, Key.A, Key.S, Key.D, Key.Jump1, Key.LMB, Key.RMB);
//
//
//
//        private final Key[] keys;
//        private int width,height;
//
//        private KeystrokesOpt(Key... keysIn){
//            this.keys = keysIn;
//                for(Key key : keysIn){
//                    this.width = Math.max(this.width, key.getX() + key.getWidth());
//                    this.height = Math.max(this.height, key.getY() + key.getHeight());
//
//                }
//        }
//    }
//
//    public static class Key {
//        public static Minecraft mc = Minecraft.getMinecraft();
//
//        private static final Key W = new Key("W", mc.gameSettings.keyBindForward, 21 ,01, 18, 1);
//        private static final Key A = new Key("A", mc.gameSettings.keyBindBack, 21 ,01, 18, 1);
//        private static final Key S = new Key("S", mc.gameSettings.keyBindLeft, 21 ,01, 18, 1);
//        private static final Key D = new Key("D", mc.gameSettings.keyBindRight, 21 ,01, 18, 1);
//
//        private static final Key LMB = new Key("LMB", mc.gameSettings.keyBindAttack, 1 ,41, 28, 18);
//        private static final Key RMB = new Key("RMB", mc.gameSettings.keyBindUseItem, 31 ,41, 28, 18);
//
//        private static final Key Jump1 = new Key("---", mc.gameSettings.keyBindForward, 1 ,41, 58, 18);
//
//        private final String name;
//        private final KeyBinding key;
//        private final int x,y,w,h;
//
//        public Key(String name, KeyBinding key, int x, int y, int w, int h) {
//            this.name = name;
//            this.key = key;
//            this.x = x;
//            this.y = y;
//            this.w = w;
//            this.h = h;
//        }
//
//        public Boolean isDown(){
//            return key.isKeyDown();
//        }
//
//        public int getHeight(){
//            return h;
//        }
//
//        public int getWidth(){
//            return w;
//        }
//
//        public int getX(){
//            return x;
//        }
//
//        public int getY(){
//            return y;
//        }
//
//    }
//
//    private KeystrokesOpt opt = KeystrokesOpt.WASD_JUMP_MOUSE;
//
//    public void setOpt(KeystrokesOpt opt) {
//        this.opt = opt;
//    }
//
//    private List<Long> clicks = new ArrayList<Long>();
//    private boolean wasPressed;
//    private long lastPressed;
//
//    private List<Long> clicks2 = new ArrayList<Long>();
//    private boolean wasPressed2;
//    private long lastPressed2;
//
//    private FontRenderer fr = new FontRenderer(FontRenderer.ROBOTO, 2);
//
//    @Override
//    public void render() {
//        if(opt.equals("WASD")) {
//            this.setOpt(opt.WASD);
//        } else if(opt.equals("WASD JUMP")) {
//            this.setOpt(opt.WASD_JUMP);
//        } else if(opt.equals("WASD JUMP MOUSE")) {
//            this.setOpt(opt.WASD_JUMP_MOUSE);
//        } else if(opt.equals("WASD MOUSE")) {
//            this.setOpt(opt.WASD_MOUSE);
//        }
//
//        final boolean lpressed = Mouse.isButtonDown(0);
//        final boolean rpressed = Mouse.isButtonDown(1);
//
//        if(lpressed != this.wasPressed) {
//            this.lastPressed = System.currentTimeMillis() + 10;
//            this.wasPressed = lpressed;
//            if(lpressed) {
//                this.clicks.add(this.lastPressed);
//            }
//        }
//
//        if(rpressed != this.wasPressed2) {
//            this.lastPressed2 = System.currentTimeMillis() + 10;
//            this.wasPressed2 = rpressed;
//            if(rpressed) {
//                this.clicks2.add(this.lastPressed2);
//            }
//        }
//
//        GL11.glPushMatrix();
//
//        for(Key key : opt.keys) {
//
//            int textWidth = (int) fr.getWidth(key.name);
//
//            Gui.drawRect(
//                    this.getX() + key.getX(),
//                    this.getY() + key.getY(),
//                    this.getX() + key.getX() + key.getWidth(),
//                    this.getY() + key.getY() + key.getHeight(),
//                    key.isDown() ? new Color(255, 255, 255, 102).getRGB() : new Color(0, 0, 0, 150).getRGB()
//            );
//
//            if(!key.name.matches("§m-----")) {
//                fr.drawString(
//                        this.getX() + key.getX() + key.getWidth() / 2 - textWidth / 2 - 0.5f,
//                        this.getY() + key.getY() + key.getHeight() / 2 - 4,
//                        key.name
//                //,key.isDown() ? Color.BLACK.getRGB() : Color.WHITE.getRGB()
//                );
//
//
//            } else {
//                fr.drawString(
//                        this.getX() + key.getX() + key.getWidth() / 2 - textWidth / 2 - 0.5f,
//                        this.getY() + key.getY() + key.getHeight() / 2 - 4,
//                        key.name
//                        //,key.isDown() ? Color.BLACK.getRGB() : Color.WHITE.getRGB()
//                );
//
//            }
//                GlStateManager.popMatrix();
//            }
//
//        }
//
//    @EventSubscriber
//    public void onKeyDown (KeyPressedEvent event) {
//        if (event.getKeyCode() == Keyboard.KEY_RSHIFT) this.toggle();
//    }
//
//    @Override
//    public void onEnabled() {
//        Evie.log("Keystrokes", "Enabled");
//        super.onEnabled();
//        switch(opt){
//            case WASD:
//                opt = KeystrokesOpt.WASD_MOUSE;
//                break;
//            case WASD_MOUSE:
//                opt = KeystrokesOpt.WASD_JUMP;
//                break;
//            case WASD_JUMP:
//                opt = KeystrokesOpt.WASD_JUMP_MOUSE;
//                break;
//            case WASD_JUMP_MOUSE:
//                opt = KeystrokesOpt.WASD;
//                break;
//        }
//    }
//
//    @Override
//    public void onDisable() {
//        super.onDisable();
//        Evie.log("Keystrokes", "Disabled!");
//
//        switch(opt){
//            case WASD_MOUSE:
//                opt = KeystrokesOpt.WASD;
//                break;
//            case WASD_JUMP_MOUSE:
//                opt = KeystrokesOpt.WASD_JUMP;
//                break;
//            case WASD_JUMP:
//                opt = KeystrokesOpt.WASD_MOUSE;
//                break;
//            case WASD:
//                opt = KeystrokesOpt.WASD_JUMP_MOUSE;
//                break;
//        }
//    }
//}