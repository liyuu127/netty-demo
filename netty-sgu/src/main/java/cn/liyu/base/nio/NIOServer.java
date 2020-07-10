package cn.liyu.base.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author liyu
 * @date 2020/7/10 12:09
 * @description
 */
public class NIOServer {

    public static void main(String[] args) throws IOException {

        //创建serverSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //创建selector
        Selector selector = Selector.open();

        //绑定端口，在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        //把ServerSocketChannel注册到Selector，事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待客户端连接
        while (true) {

            //等待1s,如果没有事件发生，返回
            if (selector.select(1000) == 0) {
                System.out.println("server wait 1s, no connected");
                continue;
            }

            //获取相关的SelectionKey集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //反向获取通道
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {

                SelectionKey selectionKey = iterator.next();

                //根据事件类型进行处理
                //连接事件
                if (selectionKey.isAcceptable()) {

                    //给客户端生成一个socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    //将socketChannel注册到selector,关注事件为OP_READ，同时关联一个buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                }

                //读事件
                if (selectionKey.isReadable()) {
                    //通过key，反向得到channel
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    //获取到channel关联的buffer
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();

                    channel.read(byteBuffer);

                    System.out.println("from client :" + new String(byteBuffer.array()));

                }
                //手动从集合中移动当前的selectionKey, 防止重复操作
                iterator.remove();
            }

        }

    }
}
