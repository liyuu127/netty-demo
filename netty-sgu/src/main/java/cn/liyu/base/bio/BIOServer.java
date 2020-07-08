package cn.liyu.base.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liyu
 * @date 2020/7/8 10:49
 * @description
 */
public class BIOServer {

    private static final int SERVER_PORT = 6666;

    private static final int CORE_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 20;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 10L;

    public static void main(String[] args) throws IOException {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        System.out.println("服务端启动了 serverSocket = " + serverSocket);

        //独立的 Acceptor 线程负责监听客户端的连接
        executor.execute(() -> {

            //轮询监听
            while (true) {

                try {
                    // 阻塞方法获取新的连接
                    Socket accept = serverSocket.accept();
                    System.out.println("连接到客户端 LocalAddress= " + accept.getLocalAddress().toString());

                    //每一个链接都创建一个线程
                    executor.execute(() -> {
                        handler(accept);
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private static void handler(Socket accept) {
        try (InputStream inputStream = accept.getInputStream();) {
            int len;
            byte[] data = new byte[1024];

            while ((len = inputStream.read(data)) != -1) {
                System.out.println(new String(data, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
