package com.evieclient.mixins;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EvieTweaker implements ITweaker {

    private final ArrayList<String> args = new ArrayList<>();

    private boolean isRunningOptifine = Launch.classLoader.getTransformers().stream()
            .anyMatch(p -> p.getClass().getName().toLowerCase(Locale.ENGLISH).contains("optifine"));

    @Override
    public void acceptOptions(List<String> args, File gameDir, final File assetsDir, String profile) {
        this.args.addAll(args);
        addArg("gameDir", gameDir);
        addArg("assetsDir", assetsDir);
        addArg("version", profile);
    }

    @Override
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        MixinBootstrap.init();

        MixinEnvironment environment = MixinEnvironment.getDefaultEnvironment();
        Mixins.addConfiguration("mixins.evieclient.json");

        if (isRunningOptifine) {
            environment.setObfuscationContext("notch"); // Switch's to notch mappings
        }

        if (environment.getObfuscationContext() == null) {
            environment.setObfuscationContext("notch"); // Switch's to notch mappings
        }

        environment.setSide(MixinEnvironment.Side.CLIENT);
    }

    @Override
    public String[] getLaunchArguments() {
        return args.toArray(new String[0]);
    }

    private void addArg(String label, Object value) {
        args.add("--" + label);
        args.add(value instanceof String ? (String) value : value instanceof File ? ((File) value).getAbsolutePath() : ".");
    }
}