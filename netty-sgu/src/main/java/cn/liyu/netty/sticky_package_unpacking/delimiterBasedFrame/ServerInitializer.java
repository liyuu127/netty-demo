package cn.liyu.netty.sticky_package_unpacking.delimiterBasedFrame;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

/**
 * @author liyu
 * @date 2020/7/17 10:02
 * @description
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        String delimiter = "_$";
        pipeline.addFirst(new DelimiterBasedFrameEncoder(delimiter));
        pipeline.addFirst(new StringEncoder(Charset.forName("utf-8")));
        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.wrappedBuffer(delimiter.getBytes())));
        pipeline.addLast(new StringDecoder(Charset.forName("utf-8")));
        pipeline.addLast(new ServerHandler());
    }
}
