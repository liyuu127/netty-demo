package cn.liyu.base.nio;

import org.junit.Test;

import java.nio.IntBuffer;

/**
 * @author liyu
 * @date 2020/7/8 15:44
 * @description
 */
public class BufferTest {

    @Test
    public void intBuffer_simple_test() {
        //创建一个Buffer, 大小为 5, 即可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i + 10);
        }

        //如何从buffer读取数据
        // *将buffer转换，读写切换
        //        limit = position;
        //        position = 0;
        //        mark = -1;
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println("intBuffer.get() = " + intBuffer.get());
        }
    }
}