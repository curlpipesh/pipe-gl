package me.curlpipesh.gl.example;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import me.curlpipesh.gl.obj.OBJModel;
import me.curlpipesh.gl.shader.ShaderUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class OBJTest {
    public static void main(String[] args) throws Exception {
        OBJTest main = new OBJTest();
        main.start();
    }

    public void start() {
        try {
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
        GL11.glClearColor(0, 0, 0, 1);
        OBJModel model = new OBJModel(
                new BufferedReader(
                        new InputStreamReader(
                                getClass().getClassLoader().getResourceAsStream("me/curlpipesh/gl/example/obj/hex.obj")
                        )
                ), true
        );
        double rot = 0;
        final double SCALE = 50;
        final double INVSC = 1D / SCALE;
        final int SHADER = ShaderUtil.buildShader("me/curlpipesh/gl/example/shaders/screen.vert", "me/curlpipesh/gl/example/shaders/screen.frag");
        while (!Display.isCloseRequested()) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            GL11.glPushMatrix();
            GL20.glUseProgram(SHADER);
            GL11.glTranslated(200D, 200D, 0D);
            GL11.glRotated(20, 1, 0, 0);
            GL11.glScaled(SCALE, SCALE, SCALE);
            GL11.glCallList(model.getDisplayList());
            GL11.glScaled(INVSC, INVSC, INVSC);
            GL11.glTranslated(-200D, -200D, 0D);
            GL20.glUseProgram(0);
            GL11.glPopMatrix();

            Display.update();
            int error;
            if((error = GL11.glGetError()) != GL11.GL_NO_ERROR) {
                throw new RuntimeException(Util.translateGLErrorString(error));
            }
        }
        Display.destroy();
    }
}
