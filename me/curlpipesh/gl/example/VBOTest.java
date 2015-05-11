package me.curlpipesh.gl.example;

import me.curlpipesh.gl.util.DisplayUtil;
import me.curlpipesh.gl.vbo.Vbo;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Util;

public class VBOTest {
    public static void main(String[] args) {
        VBOTest main = new VBOTest();
        main.start();
    }

    private void start() {
        DisplayUtil.buildDisplay(800, 600);
        DisplayUtil.basicOpenGLInit();

        Vbo vbo = new Vbo(GL11.GL_QUADS)
                .color(0xFFFF0000)
                .vertex(10, 10, 0)
                .vertex(10, 110, 0)
                .vertex(110, 110, 0)
                .vertex(110, 10, 0).compile();

        System.out.println("Running!");
        while (!Display.isCloseRequested()) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            vbo.render();
            Display.sync(60);
            Display.update();
            int error;
            if((error = GL11.glGetError()) != GL11.GL_NO_ERROR) {
                throw new RuntimeException(Util.translateGLErrorString(error));
            }
        }
        Display.destroy();
    }
}
