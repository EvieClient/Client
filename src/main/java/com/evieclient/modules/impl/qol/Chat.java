package com.evieclient.modules.impl.qol;

import com.evieclient.modules.Category;
import com.evieclient.modules.Module;


public class Chat extends Module {

    public static Boolean transparentBG = true;
    public static Boolean showPlayerHeads = false;
    public static Boolean smoothChat = true;

    public Chat() {
        super("Chat", "Makes the chat better.", Category.QOL, true);
    }

}