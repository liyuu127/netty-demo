package cn.liyu.base.nio.chat;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author liyu
 * @date 2020/7/10 17:19
 * @description
 */
public class NIOChatServer {


    private Selector selector;
    private ServerSocketChannel listenChannel;

    static final int SERVER_PORT = 6667;

    public NIOChatServer() throws IOException {

        this.selector = Selector.open();
        this.listenChannel = ServerSocketChannel.open();
        listenChannel.socket().bind(new InetSocketAddress(SERVER_PORT));
        listenChannel.configureBlocking(false);
        listenChannel.register(selector, SelectionKey.OP_ACCEPT);

    }

    public static void main(String[] args) throws IOException {
        NIOChatServer nioChatServer = new NIOChatServer();
        nioChatServer.listen();
    }

    /**
     * 监听事件
     *
     * @throws IOException
     */
    public void listen() throws IOException {

        while (true) {
            int count = selector.select(2000);
            if (count > 0) {
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
                        SocketChannel socketChannel = listenChannel.accept();
                        socketChannel.configureBlocking(false);
                        //将socketChannel注册到selector,关注事件为OP_READ，同时关联一个buffer
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                        System.out.println(socketChannel.getRemoteAddress() + " 上线了");

                    }

                    //读事件
                    if (selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        channel.configureBlocking(false);
                        String s = readData(selectionKey);
                        System.out.println(channel.getRemoteAddress() + " say: " + s);
                        //转发给其他的客户端
                        sendMessageToOtherClient(s, channel);

                    }
                    //手动从集合中移动当前的selectionKey, 防止重复操作
                    iterator.remove();
                }

            } else {
                System.out.println("等待···");
            }
        }

    }

    private String readData(SelectionKey selectionKey) throws IOException {
        //通过key，反向得到channel
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        channel.configureBlocking(false);
        //获取到channel关联的buffer
        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();

        channel.read(byteBuffer);

        return new String(byteBuffer.array());
    }

    private void sendMessageToOtherClient(String msg, SocketChannel cur) {

        Set<SelectionKey> keys = selector.keys();
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        keys.forEach(
                key -> {
                    if (key.channel() instanceof SocketChannel) {


                        SocketChannel channel = (SocketChannel) key.channel();
                        if (channel != cur) {
                            try {
                                channel.write(buffer);
                            } catch (IOException e) {
                                e.printStackTrace();
                                try {
                                    System.out.println(channel.getRemoteAddress() + " 离线了");
                                    //取消注册
                                    key.cancel();
                                    //关闭通道
                                    channel.close();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                }
        );
    }


}
