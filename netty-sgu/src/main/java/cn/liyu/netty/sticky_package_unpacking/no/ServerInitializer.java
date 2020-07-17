package cn.liyu.netty.sticky_package_unpacking.no;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
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

        pipeline.addFirst(new StringEncoder(Charset.forName("utf-8")));
        pipeline.addLast(new StringDecoder(Charset.forName("utf-8")));
        pipeline.addLast(new ServerHandler());
    }
}
