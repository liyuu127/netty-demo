package cn.liyu.basic_demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author liyu
 * @date 2020/4/24 14:23
 * @description netty服务端
 */
public class NettyServer {

    public static void main(String[] args) {

        //1.创建一个线程组，用来处理网络事件（接受客户端连接）
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //2.创建一个线程组，用来处理通道IO操作
        EventLoopGroup workGroup = new NioEventLoopGroup();

        //3.创建服务端启动助手来配置参数
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap
                    //4.设置两个线程组
                    .group(bossGroup, workGroup)
                    //5.使用NioServerSocketChannel作为服务器端通道实现
                    .channel(NioServerSocketChannel.class)
                    //6.设置线程队列中等待连接的个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //7.设置保持活动链接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //8.创建一个通道初始化对象用来加载当Channel收到事件消息后，如何进行业务处理
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                                      @Override
                                      protected void initChannel(SocketChannel socketChannel) throws Exception {
                                          socketChannel.pipeline()
                                                  //9.向pipeline链中添加自定义的业务处理类handler
                                                  .addLast(new NettyServerHandler());
                                          System.out.println("server is ready");
                                      }
                                  }
                    );
            //10.同步启动服务器并绑定端口，等待客户端连接（非阻塞）
            ChannelFuture sync = serverBootstrap.bind(9999).sync();
            System.out.println("server is starting");

            //11.关闭通道，关闭线程池
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
