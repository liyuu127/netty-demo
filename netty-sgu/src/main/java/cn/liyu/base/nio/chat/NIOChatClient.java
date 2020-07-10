package cn.liyu.base.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;

/**
 * @author liyu
 * @date 2020/7/10 17:58
 * @description
 */
public class NIOChatClient {

    static final String HOST = "127.0.0.1";
    static final int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public NIOChatClient() throws IOException {
        this.selector = Selector.open();
        socketChannel.bind(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
    }
}
