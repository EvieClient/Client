package com.evieclient.mixins;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EvieTweaker implements ITweaker {

    // List of launch arguments for getLaunchArguments[]
    private final List<String> launchArguments = new ArrayList<>();

    /**
     * Accepts program run information.
     *
     * @param args      launch arguments
     * @param gameDir   client working directory
     * @param assetsDir directory of client assets
     * @param profile   client version
     **/
    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        this.launchArguments.addAll(args);

        if (!args.contains("--version") && profile != null) {
            launchArguments.add("--version");
            launchArguments.add(profile);
        }

        if (!args.contains("--assetDir") && assetsDir != null) {
            launchArguments.add("--assetDir");
            launchArguments.add(assetsDir.getAbsolutePath());
        }

        if (!args.contains("--gameDir") && gameDir != null) {
            launchArguments.add("--gameDir");
            launchArguments.add(gameDir.getAbsolutePath());
        }
    }

    /**
     * Inject mixin class load into client.
     *
     * @param classLoader {@link LaunchClassLoader} of mixin environment
     **/
    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        MixinBootstrap.init();

        MixinEnvironment env = MixinEnvironment.getDefaultEnvironment();
        Mixins.addConfiguration("mixins.evieclient.json");

        if (env.getObfuscationContext() == null) {
            env.setObfuscationContext("notch");
        }

        env.setSide(MixinEnvironment.Side.CLIENT);
    }

    /**
     * Get class to start client.
     *
     * @return class path
     **/
    @Override
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }

    /**
     * Get client launch arguments.
     *
     * @return array of arguments
     **/
    @Override
    public String[] getLaunchArguments() {
        return launchArguments.toArray(new String[0]);
    }
}
