package cn.liyu.netty.sticky_package_unpacking.delimiterBasedFrame;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author liyu
 * @date 2020/7/17 10:46
 * @description 指定分隔符编码器
 */
public class DelimiterBasedFrameEncoder extends MessageToByteEncoder<String> {

    private String delimiter;

    public DelimiterBasedFrameEncoder(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) {
        // 在响应的数据后面添加分隔符
        ctx.writeAndFlush(Unpooled.wrappedBuffer((msg + delimiter).getBytes()));
    }


}
