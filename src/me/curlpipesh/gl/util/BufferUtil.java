package me.curlpipesh.gl.util;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public interface BufferUtil {
    default ByteBuffer getByteBuffer(int capacity) {
        return BufferUtils.createByteBuffer(capacity);
    }

    default IntBuffer getIntBuffer(int capacity) {
        return BufferUtils.createIntBuffer(capacity);
    }

    default FloatBuffer getFloatBuffer(int capacity) {
        return BufferUtils.createFloatBuffer(capacity);
    }

    default DoubleBuffer getDoubleBuffer(int capacity) {
        return BufferUtils.createDoubleBuffer(capacity);
    }
}
