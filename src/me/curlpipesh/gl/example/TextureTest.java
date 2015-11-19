package me.curlpipesh.gl.example;

import me.curlpipesh.gl.tessellation.Tessellator;
import me.curlpipesh.gl.tessellation.impl.VAOTessellator;
import me.curlpipesh.gl.texture.TextureLoader;
import me.curlpipesh.gl.util.DisplayUtil;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Util;

public class TextureTest {
    public static void main(String[] args) {
        TextureTest main = new TextureTest();
        main.start();
    }

    private void start() {
        DisplayUtil.buildDisplay(800, 600);
        DisplayUtil.basicOpenGLInit();
        int texture = TextureLoader.loadTexture(TextureLoader.loadImage("/me/curlpipesh/gl/example/img/tex.png"));
        Tessellator tess = new VAOTessellator(4096);
        System.out.println("Running!");
        while(!Display.isCloseRequested()) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);


            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);

            tess.startDrawing(GL11.GL_POLYGON)

                    .addVertexWithUV(0, 0, 0, 0, 1)
                    .addVertexWithUV(256, 0, 0, 1, 1)
                    .addVertexWithUV(256, 256, 0, 1, 0)
                    .addVertexWithUV(0, 256, 0, 0, 0)

                    .bindAndDraw();

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_BLEND);

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
