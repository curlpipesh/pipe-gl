package me.curlpipesh.gl.tessellation.impl;

import me.curlpipesh.gl.tessellation.Tessellator;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class VAOTessellator implements Tessellator {
    private ByteBuffer byteBuffer;
    private IntBuffer intBuffer;
    private FloatBuffer floatBuffer;

    private boolean isColored = false;
    private boolean isTextured = false;
    private int color = 0;
    private float u = 0;
    private float v = 0;
    private int index = 0;
    private int drawMode = -1;

    private int[] raw;

    private boolean isDrawing = false;

    public VAOTessellator(int capacity) {
        capacity *= 6;
        raw = new int[capacity]; // 6 -> x, y, z, color, u, v
        byteBuffer = getByteBuffer(capacity * 4); // 4 -> sizeof(float);
        intBuffer = byteBuffer.asIntBuffer();
        floatBuffer = byteBuffer.asFloatBuffer();
        isDrawing = false;
    }

    @Override
    public Tessellator startDrawing(int mode) {
        if(isDrawing && drawMode != -1) {
            System.out.println(String.format("Current drawMode:  %s", drawMode));
            System.out.println(String.format("Current isDrawing: %s", isDrawing));
            throw new IllegalStateException("Already drawing!");
        }
        isDrawing = true;
        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        drawMode = mode;
        return this;
    }

    @Override
    public Tessellator color(int color) {
        isColored = true;
        this.color = restitchColor(color);
        return this;
    }

    /**
     * Converts colors from 0xAARRGGBB format to 0xAABBGGRR format
     * @param color
     * @return
     */
    private int restitchColor(int color) {
        int a = (color >> 24) & 255;
        int r = (color >> 16) & 255;
        int g = (color >> 8) & 255;
        int b = color & 255;
        return (a << 24) | (b << 16) | (g << 8) | r;
    }

    @Override
    public Tessellator addVertex(float x, float y, float z) {
        int index2 = index * 6; // 6 -> x, y, z, color, u, v
        raw[index2] = Float.floatToRawIntBits(x);
        raw[index2+1] = Float.floatToRawIntBits(y);
        raw[index2+2] = Float.floatToRawIntBits(z);
        raw[index2+3] = this.color;
        raw[index2+4] = Float.floatToRawIntBits(this.u);
        raw[index2+5] = Float.floatToRawIntBits(this.v);
        ++index;
        return this;
    }

    @Override
    public Tessellator addUV(float u, float v) {
        isTextured = true;
        this.u = u;
        this.v = v;
        return this;
    }

    @Override
    public Tessellator addVertexWithUV(float x, float y, float z, int u, int v) {
        addUV(u, v);
        addVertex(x, y, z);
        return this;
    }

    @Override
    public Tessellator bind() {
        int index2 = index * 6; // You should know why it's a 6 by now
        intBuffer.put(raw, 0, index2);
        byteBuffer.position(0);
        byteBuffer.limit(index2 * 4); // 4 -> sizeof(float)
        if(isColored) {
            byteBuffer.position(12);
            GL11.glColorPointer(4, true, 24, byteBuffer);
        }
        if(isTextured) {
            floatBuffer.position(4);
            GL11.glTexCoordPointer(2, 24, floatBuffer);
        }
        floatBuffer.position(0);
        GL11.glVertexPointer(3, 24, floatBuffer);
        return this;
    }

    @Override
    public Tessellator draw() {
        if(!isDrawing) {
            throw new IllegalStateException("Not drawing!");
        }
        isDrawing = false;
        GL11.glDrawArrays(drawMode, 0, index);
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
        return this;
    }

    @Override
    public Tessellator reset() {
        byteBuffer.clear();
        intBuffer.clear();
        floatBuffer.clear();
        color = 0;
        isColored = false;
        u = 0;
        v = 0;
        isTextured = false;
        index = 0;
        isDrawing = false;
        return this;
    }

    @Override
    public Tessellator bindAndDraw() {
        return bind().draw().reset();
    }
}
