package com.evieclient.utils.render;

public class AnimationUtils {
    public static float clamp(float number, float min, float max) {
        return number < min ? min : Math.min(number, max);
    }
}
