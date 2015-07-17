package me.curlpipesh.gl.example;

import org.lwjgl.opengl.*;
import me.curlpipesh.gl.shader.ShaderUtil;
import me.curlpipesh.gl.tessellation.Tessellator;
import me.curlpipesh.gl.tessellation.impl.VAOTessellator;
import me.curlpipesh.gl.util.DisplayUtil;

public class ShaderTest {
    public static void main(String[] args) throws Exception {
        ShaderTest main = new ShaderTest();
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

        int SHADER = ShaderUtil.buildShader("me/curlpipesh/gl/example/shaders/screen.vert", "me/curlpipesh/gl/example/shaders/screen.frag");
        Tessellator tess = new VAOTessellator(2*2*2*2*2*2*2*2);
        while (!Display.isCloseRequested()) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            GL20.glUseProgram(SHADER);
            {
                /*GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2d(50, 50);
                GL11.glVertex2d(150, 50);
                GL11.glVertex2d(150, 150);
                GL11.glVertex2d(50, 150);
                GL11.glEnd();*/
                tess.startDrawing(GL11.GL_QUADS)
                        .addVertex(50, 50, 0)
                        .addVertex(50, 150, 0)
                        .addVertex(150, 150, 0)
                        .addVertex(150, 50, 0)
                        .bindAndDraw();
            }
            GL20.glUseProgram(0);

            Display.update();
            int error;
            if((error = GL11.glGetError()) != GL11.GL_NO_ERROR) {
                throw new RuntimeException(Util.translateGLErrorString(error));
            }
        }
        Display.destroy();
    }
}
