package cn.liyu.base.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author liyu
 * @date 2020/7/10 14:42
 * @description
 */
public class NIOClient {

    public static void main(String[] args) throws IOException {

        //得到一个通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //连接服务器，设置连接的ip和端口
        if (!socketChannel.connect(new InetSocketAddress("127.0.0.1", 6666))) {
            while (!socketChannel.finishConnect()) {
                System.out.println("连接中···");
            }
        }

        //连接成功
        String s = "hello 李宇";
        ByteBuffer byteBuffer = ByteBuffer.wrap(s.getBytes());
        //发送数据
        socketChannel.write(byteBuffer);


    }
}
