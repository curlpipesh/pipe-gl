package me.curlpipesh.gl.example;

import me.curlpipesh.gl.tessellation.Tessellator;
import me.curlpipesh.gl.tessellation.impl.VAOTessellator;
import me.curlpipesh.gl.util.DisplayUtil;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Util;

public class VAOTest {
    public static void main(String[] args) throws Exception {
        VAOTest main = new VAOTest();
        main.start();
    }

    public void start() {
        /*try {
            Display.setDisplayMode(new DisplayMode(800, 600));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        // init OpenGL here
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 800, 0, 600, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glClearColor(0, 0, 0, 1);*/
        DisplayUtil.buildDisplay(800, 600);
        DisplayUtil.basicOpenGLInit();

        Tessellator tess = new VAOTessellator(2*2*2*2*2*2*2*2*2*2*2*2*2*2*2*2);
        while (!Display.isCloseRequested()) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            tess.startDrawing(GL11.GL_QUADS)
                    .color(0xFFFF0000)
                    .addVertex(10, 10, 0)
                    .addVertex(110, 10, 0)
                    .addVertex(110, 110, 0)
                    .addVertex(10, 110, 0)
                    .bindAndDraw();
            Display.update();
            int error;
            if((error = GL11.glGetError()) != GL11.GL_NO_ERROR) {
                throw new RuntimeException(Util.translateGLErrorString(error));
            }
        }
        Display.destroy();
    }
}
