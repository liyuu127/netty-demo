package cn.liyu.netty.sticky_package_unpacking.fixedLengthFrame;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author liyu
 * @date 2020/7/17 10:14
 * @description FixedLengthFrameDecoder
 * 对于使用固定长度的粘包和拆包场景，可以使用FixedLengthFrameDecoder，该解码一器会每次读取固定长度的消息，如果当前读取到的消息不足指定长度，
 * 那么就会等待下一个消息到达后进行补足。其使用也比较简单，只需要在构造函数中指定每个消息的长度即可。
 * 这里需要注意的是，FixedLengthFrameDecoder只是一个解码一器，Netty也只提供了一个解码一器，这是因为对于解码是需要等待下一个包的进行补全的，代码相对复杂，
 * 而对于编码器，用户可以自行编写，因为编码时只需要将不足指定长度的部分进行补全即可。
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
