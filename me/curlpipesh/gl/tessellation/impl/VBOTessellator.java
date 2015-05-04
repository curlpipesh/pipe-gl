package me.curlpipesh.gl.tessellation.impl;

import org.lwjgl.opengl.GL12;
import me.curlpipesh.gl.tessellation.Tessellator;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;

@Deprecated
public class VBOTessellator implements Tessellator {
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    private FloatBuffer textureBuffer;

    private boolean isColored = false;
    private boolean isTextured = false;
    private int color = 0;
    private float u = 0;
    private float v = 0;
    private int index = 0;
    private int drawMode = -1;

    private static final int VERTEX = GL15.glGenBuffers();
    private static final int TEXTURE = GL15.glGenBuffers();
    private static final int COLOR = GL15.glGenBuffers();

    public VBOTessellator(int capacity) {
        capacity *= 4;
        vertexBuffer = getFloatBuffer(capacity * 3);
        colorBuffer = getFloatBuffer(capacity * 4);
        textureBuffer = getFloatBuffer(capacity * 2);
    }

    @Override
    public Tessellator startDrawing(int mode) {
        drawMode = mode;
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL15.GL_ELEMENT_ARRAY_BUFFER);
        return this;
    }

    @Override
    public Tessellator color(int color) {
        isColored = true;
        this.color = color;
        return this;
    }

    @Override
    public Tessellator addVertex(float x, float y, float z) {
        vertexBuffer.put(x).put(y).put(z);
        float[] col = getColor(color);
        colorBuffer.put(col[0]).put(col[1]).put(col[2]).put(col[3]);
        textureBuffer.put(u).put(v);
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
        int index2 = index * 3;
        vertexBuffer.position(0);
        vertexBuffer.limit(index2 * 4); // 4 -> sizeof(float)

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, VERTEX);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
        //GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);

        if(isColored) {
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, COLOR);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorBuffer, GL15.GL_STATIC_DRAW);
            //GL11.glColorPointer(4, GL11.GL_FLOAT, 0, 12);
        }
        if(isTextured) {
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, TEXTURE);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureBuffer, GL15.GL_STATIC_DRAW);
            //GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 16);
        }

        return this;
    }

    @Override
    public Tessellator draw() {
        //GL11.glDrawElements(drawMode, vertexBuffer);
        //GL11.glDrawArrays(drawMode, 0, index);
        //GL11.glDrawElements(drawMode, index, GL11.GL_UNSIGNED_INT, 0L);
        GL12.glDrawRangeElements(drawMode, 0, index, index, GL11.GL_UNSIGNED_INT, 0L);
        //GL15.glDeleteBuffers(VBO_ID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL11.glDisableClientState(GL15.GL_ELEMENT_ARRAY_BUFFER);
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
        GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        return this;
    }

    @Override
    public Tessellator reset() {
        vertexBuffer.clear();
        color = 0;
        isColored = false;
        u = 0;
        v = 0;
        isTextured = false;
        index = 0;
        return this;
    }

    @Override
    public Tessellator bindAndDraw() {
        return bind().draw().reset();
    }

    private float[] getColor(final int par4) {
        final float var10 = ((par4 >> 24) & 255) / 255.0F;
        final float var6 = ((par4 >> 16) & 255) / 255.0F;
        final float var7 = ((par4 >> 8) & 255) / 255.0F;
        final float var8 = (par4 & 255) / 255.0F;
        return new float[] {var6, var7, var8, var10};
    }
}
