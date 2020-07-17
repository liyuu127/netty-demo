package cn.liyu.netty.sticky_package_unpacking.fixedLengthFrame;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

/**
 * @author liyu
 * @date 2020/7/17 10:22
 * @description
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 对客户端发送的消息进行空格补全，保证其长度为20
        pipeline.addFirst(new FixedLengthFrameEncoder(20));
        pipeline.addFirst(new StringEncoder(Charset.forName("utf-8")));
        // 对服务端发送的消息进行粘包和拆包处理，由于服务端发送的消息已经进行了空格补全，
        // 并且长度为20，因而这里指定的长度也为20
        ch.pipeline().addLast(new FixedLengthFrameDecoder(20));
        pipeline.addLast(new StringDecoder(Charset.forName("utf-8")));
        pipeline.addLast(new ClientHandler());
    }
}
