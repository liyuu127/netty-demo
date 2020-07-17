package cn.liyu.netty.sticky_package_unpacking.no;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author liyu
 * @date 2020/7/17 9:55
 * @description
 */
public class Server {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ChannelFuture channelFuture = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列得到连接个数
                .childOption(ChannelOption.SO_KEEPALIVE, true) //设置保持活动连接状态
                .childHandler(new ServerInitializer())
                .bind(6666).sync();

        channelFuture.channel().closeFuture().sync();
    }
}
