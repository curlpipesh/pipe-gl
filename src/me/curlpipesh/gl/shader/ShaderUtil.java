package me.curlpipesh.gl.shader;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

@SuppressWarnings("unused")
public final class ShaderUtil {
    private static boolean debug = false;

    public static int loadShader(String file, ShaderType type) {
        int shaderProgram = ARBShaderObjects.glCreateShaderObjectARB(
                type.equals(ShaderType.FRAGMENT)
                        ? ARBFragmentShader.GL_FRAGMENT_SHADER_ARB
                        : ARBVertexShader.GL_VERTEX_SHADER_ARB
        );

        ByteBuffer shaderTextBuffer = stringToByteBuffer(fileToString(file));
        ARBShaderObjects.glShaderSourceARB(shaderProgram, shaderTextBuffer);
        ARBShaderObjects.glCompileShaderARB(shaderProgram);
        printLogInfo(shaderProgram);
        return shaderProgram;
    }

    public static int buildShader(String vertex, String fragment) {
        int vert = loadShader(vertex, ShaderType.VERTEX);
        int frag = loadShader(fragment, ShaderType.FRAGMENT);
        int program = ARBShaderObjects.glCreateProgramObjectARB();
        ARBShaderObjects.glAttachObjectARB(program, vert);
        ARBShaderObjects.glAttachObjectARB(program, frag);
        ARBShaderObjects.glLinkProgramARB(program);
        // ARBShaderObjects.glValidateProgramARB(program);
        return program;
    }

    private static void printLogInfo(int obj) {
        if(!debug) {
            return;
        }
        IntBuffer iVal = BufferUtils.createIntBuffer(1);
        ARBShaderObjects.glGetObjectParameterARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB, iVal);

        int length = iVal.get();
        System.out.println("Info log length:"+length);
        if (length > 0)	{
            ByteBuffer infoLog = BufferUtils.createByteBuffer(length);
            iVal.flip();
            ARBShaderObjects.glGetInfoLogARB(obj,  iVal, infoLog);
            byte[] infoBytes = new byte[length];
            infoLog.get(infoBytes);
            String out = new String(infoBytes);
            System.out.println("Info log:\n"+out);
        }
        Util.checkGLError();
    }

    private static String fileToString(String filename) {
        try {
            /*new FileReader(filename)*/
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            ShaderUtil.class.getClassLoader().getResourceAsStream(filename)
                    )
            );
            String line, res = "";
            while ((line=br.readLine()) != null) {
                res += line + "\n";
            }
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static ByteBuffer stringToByteBuffer(String s) {
        ByteBuffer bb = ByteBuffer.allocateDirect(s.length());
        try {
            bb.put(s.getBytes("US-ASCII"));
        } catch (UnsupportedEncodingException e) {
            //return null;
            throw new IllegalArgumentException(e);
        }
        return (ByteBuffer) bb.flip();
    }

    public static void setDebug(boolean b) {
        debug = b;
    }

    public static boolean getDebug() {
        return debug;
    }

    public static enum ShaderType {
        FRAGMENT, VERTEX
    }
}
