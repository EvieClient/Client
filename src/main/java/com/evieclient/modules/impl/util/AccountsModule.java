package com.evieclient.modules.impl.util;

import com.evieclient.Evie;
import com.evieclient.events.bus.EventSubscriber;
import com.evieclient.events.impl.world.LoadWorldEvent;
import com.evieclient.modules.Category;
import com.evieclient.modules.Module;
import com.evieclient.events.impl.client.GameLoopEvent;
import com.evieclient.utils.SessionChanger;
import io.sentry.Sentry;
import net.minecraft.client.Minecraft;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class AccountsModule extends Module {

    public AccountsModule() {
        super("Accounts", "Changes the current logged-in account.", Category.UTIL, true);
    }

    @Override public void setupModule () {
        try {
            SessionChanger.getInstance().setUserOffline("evieclient");
        } catch (IllegalAccessException e) {
            Sentry.captureException(e);
        }

    }
}