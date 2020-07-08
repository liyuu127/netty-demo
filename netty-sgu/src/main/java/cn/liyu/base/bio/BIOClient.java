package cn.liyu.base.bio;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * @author liyu
 * @date 2020/7/8 11:40
 * @description
 */
public class BIOClient {

    public static void main(String[] args) {


        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 6666);
                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + ": hello world").getBytes());
                        Thread.sleep(2000);
                    } catch (Exception e) {
                    }
                }
            } catch (IOException e) {
            }
        }).start();

    }

}


