package me.curlpipesh.gl.util;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

/**
 * @author audrey
 * @since 12.01.15
 */
public final class DisplayUtil {
    public static void buildDisplay(int width, int height) {
        buildDisplay(width, height, new PixelFormat());
    }

    public static void buildDisplay(int width, int height, PixelFormat pixelFormat) {
        buildDisplay(width, height, pixelFormat, null, null);
    }

    public static void buildDisplay(int width, int height, PixelFormat pixelFormat, Drawable sharedDrawable) {
        buildDisplay(width, height, pixelFormat, sharedDrawable, null);
    }

    public static void buildDisplay(int width, int height, PixelFormat pixelFormat, ContextAttribs attribs) {
        buildDisplay(width, height, pixelFormat, null, attribs);
    }

    public static void buildDisplay(int width, int height, PixelFormat pixelFormat, Drawable sharedDrawable, ContextAttribs attribs) {
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create(pixelFormat, sharedDrawable, attribs);
            GL11.glViewport(0, 0, width, height);
        } catch (LWJGLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void basicOpenGLInit() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 800, 0, 600, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
    }
}
