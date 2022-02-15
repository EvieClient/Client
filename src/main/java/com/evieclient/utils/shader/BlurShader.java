package com.evieclient.utils.shader;

import com.evieclient.utils.ShaderProgram;
import java.io.InputStream;

public class BlurShader extends ShaderProgram {
    static ClassLoader classLoader = BlurShader.class.getClassLoader();
    private static final InputStream VERTEX_FILE = classLoader.getResourceAsStream("shaders/hblur.vsh");
    private static final InputStream FRAGMENT_FILE = classLoader.getResourceAsStream("shaders/hblur.fsh");

    public BlurShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        // super.bindAttribute(0, "Something")
    }
}
