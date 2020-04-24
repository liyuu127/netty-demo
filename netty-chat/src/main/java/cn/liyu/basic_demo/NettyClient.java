package cn.liyu.basic_demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author liyu
 * @date 2020/4/24 15:34
 * @description
 */
public class NettyClient {

    public static void main(String[] args) throws Exception {
        //1.创建一个 EventLoopGroup 线程组
        EventLoopGroup group = new NioEventLoopGroup();
        //2.创建客户端启动助手
        Bootstrap b = new Bootstrap();
        //3.设置 EventLoopGroup 线程组
        b.group(group)
                //4.使用 NioSocketChannel 作为客户端通道实现
                .channel(NioSocketChannel.class)
                //5.创建一个通道初始化对象
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) {
                        //6.往 Pipeline 链中添加自定义的业务
                        //处理 handler
                        //客户端业务处理类
                        sc.pipeline().addLast(new NettyClientHandler());
                        System.out.println("......Client is ready.......");
                    }
                });
        //7.启动客户端,等待连接上服务器端(非阻塞)
        ChannelFuture cf = b.connect("127.0.0.1", 9999).sync();
        //8.等待连接关闭(非阻塞)
        cf.channel().closeFuture().sync();
    }
}

