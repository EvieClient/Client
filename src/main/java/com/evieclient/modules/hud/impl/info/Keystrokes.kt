package com.evieclient.modules.hud.impl.info

import com.evieclient.modules.Category
import com.evieclient.modules.hud.RenderModule
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.settings.KeyBinding
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.GL11
import java.awt.Color
import java.util.*

class Keystrokes : RenderModule(
    "Keystrokes",
    "Show keys that are being pressed",
    Category.INFO,
    true
) {
    var leftCps: Boolean = true
    var rightCps: Boolean = true

    enum class KeystrokesMode(vararg keysIn: Key) {
        WASD_JUMP_MOUSE(
            Key.W,
            Key.A,
            Key.S,
            Key.LMB,
            Key.RMB,
            Key.D,
            Key("§m-----", Minecraft.getMinecraft().gameSettings.keyBindJump, 1, 61, 58, 18, false)
        );


        val keys: Array<Key>;

        init {
            keys = keysIn as Array<Key>
        }
    }

    class Key(
        val name: String,
        private val keyBind: KeyBinding,
        val x: Int,
        val y: Int,
        val width: Int,
        val height: Int,
        val cps: Boolean
    ) {

        val isDown: Boolean
            get() = keyBind.isKeyDown

        companion object {
            val W = Key("W", Minecraft.getMinecraft().gameSettings.keyBindForward, 21, 1, 18, 18, false)
            val A = Key("A", Minecraft.getMinecraft().gameSettings.keyBindLeft, 1, 21, 18, 18, false)
            val S = Key("S", Minecraft.getMinecraft().gameSettings.keyBindBack, 21, 21, 18, 18, false)
            val D = Key("D", Minecraft.getMinecraft().gameSettings.keyBindRight, 41, 21, 18, 18, false)
            val LMB = Key("LMB", Minecraft.getMinecraft().gameSettings.keyBindAttack, 1, 41, 28, 18, true)
            val RMB = Key("RMB", Minecraft.getMinecraft().gameSettings.keyBindUseItem, 31, 41, 28, 18, true)
        }
    }

    private val mode = KeystrokesMode.WASD_JUMP_MOUSE

    override fun getWidth(): Int {
        return 100
    }

    override fun getHeight(): Int {
        return 200
    }

    private val clicks: MutableList<Long> = ArrayList()
    private var wasPressed = false
    private var lastPressed: Long = 0
    private val clicks2: MutableList<Long> = ArrayList()
    private var wasPressed2 = false
    private var lastPressed2: Long = 0

    override fun render() {
        val lpressed = Mouse.isButtonDown(0)
        val rpressed = Mouse.isButtonDown(1)
        if (lpressed != wasPressed) {
            lastPressed = System.currentTimeMillis() + 10
            wasPressed = lpressed
            if (lpressed) {
                clicks.add(lastPressed)
            }
        }
        if (rpressed != wasPressed2) {
            lastPressed2 = System.currentTimeMillis() + 10
            wasPressed2 = rpressed
            if (rpressed) {
                clicks2.add(lastPressed2)
            }
        }
        GL11.glPushMatrix()
        for (key in mode.keys) {
            val textWidth =
                fr.getStringWidth(key.name)
            Gui.drawRect(
                this.x + key.x,
                this.y + key.y,
                this.x + key.x + key.width,
                this.y + key.y + key.height,
                if (key.isDown) Color(255, 255, 255, 102).rgb else Color(0, 0, 0, 150).rgb
            )
                fr.drawString(
                    key.name,
                    this.x + key.x + key.width / 2 - textWidth / 2,
                    this.y + key.y + key.height / 2 - 4,
                    if (key.isDown) Color.PINK.rgb else Color.WHITE.rgb
                )
            if (key.cps) {
                GlStateManager.pushMatrix()
                GlStateManager.scale(0.5f, 0.5f, 0.5f)
                GlStateManager.translate(
                    this.x + key.x + key.width / 2 - textWidth / 2f,
                    this.y + key.y + key.height / 2 + 4f,
                    1f
                )
                GlStateManager.popMatrix();
                if (key.name.matches(Regex(Key.LMB.name)) && leftCps) {
                    fr.drawString(
                        "$cPS",
                        this.x + key.x + key.width / 2 - textWidth / 2,
                        this.y + key.y + key.height / 2 + 4,
                        if (key.isDown) Color.PINK.rgb else Color.WHITE.rgb
                    )
                }
            }
            if (key.name.matches(Regex(Key.RMB.name)) && rightCps) {
                fr.drawString(
                    "$cPS2",
                    this.x + key.x + key.width / 2 - textWidth / 2,
                    this.y + key.y + key.height / 2 + 4,
                    if (key.isDown) Color.PINK.rgb else Color.WHITE.rgb
                )
            }
        }
        GL11.glPopMatrix();
    }

    private val cPS: Int
        get() {
            val time = System.currentTimeMillis()
            clicks.removeIf { aLong: Long -> aLong + 1000 < time }
            return clicks.size
        }
    private val cPS2: Int
        get() {
            val time2 = System.currentTimeMillis()
            clicks2.removeIf { aLong2: Long -> aLong2 + 1000 < time2 }
            return clicks2.size
        }
}
