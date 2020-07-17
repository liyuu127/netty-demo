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
 * @date 2020/7/17 10:22
 * @description
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        String delimiter = "_$";
        ChannelPipeline pipeline = ch.pipeline();
        // 这是我们自定义的一个编码器，主要作用是在返回的响应数据最后添加分隔符
        pipeline.addFirst(new DelimiterBasedFrameEncoder(delimiter));
        pipeline.addFirst(new StringEncoder(Charset.forName("utf-8")));
        // 将delimiter设置到DelimiterBasedFrameDecoder中，经过该解码一器进行处理之后，源数据将会
        // 被按照_$进行分隔，这里1024指的是分隔的最大长度，即当读取到1024个字节的数据之后，若还是未
        // 读取到分隔符，则舍弃当前数据段，因为其很有可能是由于码流紊乱造成的
        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.wrappedBuffer(delimiter.getBytes())));
        pipeline.addLast(new StringDecoder(Charset.forName("utf-8")));
        pipeline.addLast(new ClientHandler());
    }
}
