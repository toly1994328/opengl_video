package com.toly1994.opengl_video.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class BufferUtils {
    /**
     * short数组缓冲数据
     *
     * @param array short 数组
     * @return 获取short缓冲数据
     */
    public static ShortBuffer getShortBuffer(short[] array) {
        ShortBuffer buffer;
        ByteBuffer qbb = ByteBuffer.allocateDirect(array.length * 2);
        qbb.order(ByteOrder.nativeOrder());
        buffer = qbb.asShortBuffer();
        buffer.put(array);
        buffer.position(0);
        return buffer;
    }

    /**
     * @param array int数组
     * @return 获取整形缓冲数据
     */
    public static IntBuffer getIntBuffer(int[] array) {
        IntBuffer buffer;
        ByteBuffer qbb = ByteBuffer.allocateDirect(array.length * 4);
        qbb.order(ByteOrder.nativeOrder());
        buffer = qbb.asIntBuffer();
        buffer.put(array);
        buffer.position(0);
        return buffer;
    }

    /**
     * float数组缓冲数据
     *
     * @param array 顶点
     * @return 获取浮点形缓冲数据
     */
    public static FloatBuffer getFloatBuffer(float[] array) {
        FloatBuffer buffer;
        ///每个浮点数:坐标个数* 4字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(array.length * 4);
        //使用本机硬件设备的字节顺序
        qbb.order(ByteOrder.nativeOrder());
        // 从字节缓冲区创建浮点缓冲区
        buffer = qbb.asFloatBuffer();
        // 将坐标添加到FloatBuffer
        buffer.put(array);
        //设置缓冲区以读取第一个坐标
        buffer.position(0);
        return buffer;
    }

    /**
     * @param array Byte 数组
     * @return 获取字节型缓冲数据
     */
    public static ByteBuffer getByteBuffer(byte[] array) {
        ByteBuffer buffer ;
        buffer = ByteBuffer.allocateDirect(array.length);
        buffer.put(array);
        buffer.position(0);
        return buffer;
    }
}
