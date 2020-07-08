package cn.liyu.base.nio;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author liyu
 * @date 2020/7/8 15:51
 * @description
 */
public class ChannelTest {

    /**
     * channel 写入文件
     *
     * @throws IOException
     */
    @Test
    public void nio_file_channel_simple_write() throws IOException {

        String str = "hello liyu nn 一旦读完了所有的数据，就需要清空缓冲区，让它可以再次被写入。有两种方式能清空缓冲区：调用clear()或compact()方法。clear()方法会清空整个缓冲区。compact()方法只会清除已经读过的数据。任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面。";
        String path = "E:\\test\\file01.txt";
        //判断文件夹是否存在
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            boolean mkdirs = file.getParentFile().mkdirs();
            System.out.println("mkdirs = " + mkdirs);
        }
        //创建一个输出流
        FileOutputStream fos = new FileOutputStream(path);

        //通过输出流获取对应的channel
        FileChannel fileChannel = fos.getChannel();
        //创建一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(12);
        byte[] bytes = str.getBytes();
        for (int i = 0; i < bytes.length / 12; i++) {

            //将str放入buffer
            buffer.put(str.getBytes(), i * 12, 12);
            //对buffer进行flip
            buffer.flip();
            //将buffer数据写入channel
            fileChannel.write(buffer);
            buffer.clear();
        }
        //将str放入buffer
        buffer.put(str.getBytes(), bytes.length - bytes.length % 12, bytes.length % 12);
        //对buffer进行flip
        buffer.flip();
        //将buffer数据写入channel
        fileChannel.write(buffer);
        //关闭流
        fos.close();
    }

    @Test
    public void nio_file_channel_simple_read() throws IOException {

        String path = "E:\\test\\file01.txt";

        //创建一个输出流
        FileInputStream fis = new FileInputStream(path);

        //通过输出流获取对应的channel
        FileChannel fileChannel = fis.getChannel();
        //创建一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(10);
        //channe写入buffer
        int len;
        while ((len = fileChannel.read(buffer)) != -1) {
            String s = new String(buffer.array(), 0, len);
            System.out.println(s);
            buffer.clear();
        }
        //关闭流
        fis.close();
    }

    @Test
    public void nio_file_channel_simple_read_and_write() throws IOException {

        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true) { //循环读取

            //这里有一个重要的操作，一定不要忘了
            /*
             public final Buffer clear() {
                position = 0;
                limit = capacity;
                mark = -1;
                return this;
            }
             */
            byteBuffer.clear(); //清空buffer
            int read = fileChannel01.read(byteBuffer);
            System.out.println("read =" + read);
            if (read == -1) { //表示读完
                break;
            }
            //将buffer 中的数据写入到 fileChannel02 -- 2.txt
            byteBuffer.flip();
            fileChannel02.write(byteBuffer);
        }

        //关闭相关的流
        fileInputStream.close();
        fileOutputStream.close();
    }

    @Test
    public void nio_file_channel_copy() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        fileChannel01.transferTo(0, fileChannel01.size(), fileChannel02);


        //关闭相关的流
        fileInputStream.close();
        fileOutputStream.close();
    }

}
