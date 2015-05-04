package me.curlpipesh.gl.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public interface BufferUtil {
    public default ByteBuffer getByteBuffer(int capacity) {
        return ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder());
    }

    public default IntBuffer getIntBuffer(int capacity) {
        return getByteBuffer(capacity).asIntBuffer();
    }

    public default FloatBuffer getFloatBuffer(int capacity) {
        return getByteBuffer(capacity).asFloatBuffer();
    }
}
