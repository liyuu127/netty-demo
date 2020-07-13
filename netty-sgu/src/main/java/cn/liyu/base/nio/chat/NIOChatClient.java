package cn.liyu.base.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

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
        this.socketChannel = SocketChannel.open();
        this.socketChannel.connect(new InetSocketAddress(HOST, PORT));
        this.socketChannel.configureBlocking(false);
        this.selector = Selector.open();
        this.socketChannel.register(selector, SelectionKey.OP_READ);
        this.username = this.socketChannel.getLocalAddress().toString();
        System.out.println(username + " is ok");
    }

    public void sendMsg(String message) throws IOException {
        message = username + " say: " + message;
        socketChannel.write(ByteBuffer.wrap(message.getBytes()));
    }

    public void read() throws IOException {
        int select = selector.select();
        if (select > 0) {

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                SocketChannel channel = (SocketChannel) key.channel();
                ByteBuffer allocate = ByteBuffer.allocate(1024);
                channel.read(allocate);
                String s = new String(allocate.array());
                System.out.println(s.trim());

            }

        }
    }

    public static void main(String[] args) throws IOException {
        NIOChatClient nioChatClient = new NIOChatClient();
        while (true) {

            new Thread(() -> {
                try {
                    nioChatClient.read();
                    Thread.sleep(3000);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                nioChatClient.sendMsg(scanner.nextLine());
            }
        }
    }
}
