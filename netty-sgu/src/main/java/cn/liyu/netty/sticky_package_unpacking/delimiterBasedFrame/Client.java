package cn.liyu.netty.sticky_package_unpacking.delimiterBasedFrame;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author liyu
 * @date 2020/7/17 10:14
 * @description LineBasedFrameDecoder与DelimiterBasedFrameDecoder
 * 对于通过分隔符进行粘包和拆包问题的处理，Netty提供了两个编解码的类，LineBasedFrameDecoder和DelimiterBasedFrameDecoder。
 * 这里LineBasedFrameDecoder的作用主要是通过换行符，即\n或者\r\n对数据进行处理；
 * 而DelimiterBasedFrameDecoder的作用则是通过用户指定的分隔符对数据进行粘包和拆包处理。
 * 同样的，这两个类都是解码一器类，而对于数据的编码，也即在每个数据包最后添加换行符或者指定分割符的部分需要用户自行进行处理。
 * 这里以DelimiterBasedFrameDecoder为例进行讲解
 */
public class Client {

    public static void main(String[] args) {

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientInitializer());
            ChannelFuture sync = bootstrap.connect("127.0.0.1", 6666).sync();
            String msg = "hello";
            Channel channel = sync.channel();
            for (int i = 0; i < 10; i++) {
                channel.writeAndFlush(msg);
            }
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }
}
