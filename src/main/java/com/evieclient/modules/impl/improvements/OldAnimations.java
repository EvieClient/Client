package com.evieclient.modules.impl.improvements;

import com.evieclient.modules.Category;
import com.evieclient.modules.Module;


public class OldAnimations extends Module {
    public OldAnimations() {
        super("1.7 Animations", "Gives you back the feeling of 1.7 :)", Category.IMPROVEMENTS, true);
    }

    @Override
    public OldAnimations setupModule() {
     //   Evie.EVENT_BUS.register(new AnimationEventHandler());
        return this;
    }
}