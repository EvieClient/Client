package com.evieclient.utils;

import io.sentry.Sentry;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.*;

/**
 * Loads shaders.
 *
 * @since 1.0.0
 **/

public abstract class ShaderProgram {
    private final int programID;
    private final int vertexShaderID;
    private final int fragmentShaderID;

    /**
     * Loads and links the shaders.
     *
     * @param vertexFile
     * @param fragmentFile
     **/
    public ShaderProgram(InputStream vertexFile, InputStream fragmentFile) {
        vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);
        bindAttributes();
    }

    /**
     * Load a shader.
     *
     * @param file This is the path to the shader file
     * @param type This is the type of shader
     **/
    private static int loadShader(InputStream file, int type) {
        StringBuilder shaderSource = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            Sentry.captureException(e);
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            Sentry.captureException(e);
            e.printStackTrace();
            System.exit(-1);
        }

        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);

        // This is depreciated, but it doesn't really matter for what this is trying to do
        if (GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println((GL20.glGetShaderInfoLog(shaderID, 500)));
            System.err.println("Could not compile shader.");
            System.exit(-1);
        }
        return shaderID;
    }

    /**
     * Starts the shader.
     **/
    public void start() {
        GL20.glUseProgram(programID);
    }

    /**
     * Stops the shader.
     **/
    public void stop() {
        GL20.glUseProgram(0);
    }

    /**
     * Cleans up the shader.
     **/
    public void cleanUp() {
        stop();
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);
    }

    protected abstract void bindAttributes();

    protected void bindAttribute(int attribute, String variableName) {
        GL20.glBindAttribLocation(programID, attribute, variableName);
    }
}
