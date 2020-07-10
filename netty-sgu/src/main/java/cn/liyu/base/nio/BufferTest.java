package cn.liyu.base.nio;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

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

    @Test
    public void read_only_buffer_test() {
        //创建一个buffer
        ByteBuffer buffer = ByteBuffer.allocate(128);

        for (int i = 0; i < 64; i++) {
            buffer.put((byte) i);
        }

        //读取
        buffer.flip();

        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        //读取
        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }
        //java.nio.ReadOnlyBufferException
        //readOnlyBuffer.put((byte) 3);
    }

    /**
     * 缓冲区操作直接内存
     */
    @Test
    public void direct_buffer_test() throws IOException {


        RandomAccessFile randomAccessFile = new RandomAccessFile("/1.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        /**
         * 参数1: FileChannel.MapMode.READ_WRITE 使用的读写模式
         * 参数2： 0 ： 可以直接修改的起始位置
         * 参数3:  5: 是映射到内存的大小(不是索引位置) ,即将 1.txt 的多少个字节映射到内存
         * 可以直接修改的范围就是 0-5
         * 实际类型 DirectByteBuffer
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(1, (byte) 'H');
        map.put(2, (byte) 'H');
        randomAccessFile.close();
    }

    /**
     * 分散聚合
     * scattering:数据写入buffer时，可以采用buffer数组，依次写入
     * gathering:buffer读取时，可以采用buffer数组，依次读
     */
    @Test
    public void buffer_scattering_and_gathering_test() throws IOException {

        //使用serverSocketChannel和socketChannel网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        //绑定端口到socket,并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //等待客户端连接
        SocketChannel accept = serverSocketChannel.accept();

        while (true) {
            int byteRead = 0;
            while (byteRead < 8) {
                long read = accept.read(byteBuffers);
                byteRead += read;
                System.out.println("byteRead = " + byteRead);
                Arrays.stream(byteBuffers).forEach(o -> {
                    System.out.println("o.position() = " + o.position());
                    System.out.println("o.limit() = " + o.limit());
                });
            }
            Arrays.stream(byteBuffers).forEach(o -> o.flip());

            //数据显示到客户端
            long write = 0;
            while (write < 8) {
                long write1 = accept.write(byteBuffers);
                write += write1;
            }
            Arrays.stream(byteBuffers).forEach(o -> o.clear());
            System.out.println("byteRead = " + byteRead + "\t" + "write = " + write);

        }

    }

}