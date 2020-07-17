package cn.liyu.netty.sticky_package_unpacking.fixedLengthFrame;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author liyu
 * @date 2020/7/17 10:46
 * @description 定长编码器
 */
public class FixedLengthFrameEncoder extends MessageToMessageEncoder<String> {

    private int length;

    public FixedLengthFrameEncoder(int length) {
        this.length = length;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {

        // 对于超过指定长度的消息，这里直接抛出异常
        if (msg.length() > length) {
            throw new UnsupportedOperationException("message length is too large, it's limited " + length);
        }
        // 如果长度不足，则进行补全
        if (msg.length() < length) {
            msg = addSpace(msg);
        }

        ctx.writeAndFlush(Unpooled.wrappedBuffer(msg.getBytes()));
    }

    /** 进行空格补全
     *
     * @param msg
     * @return
     */
    private String addSpace(String msg) {
        StringBuilder builder = new StringBuilder(msg);
        for (int i = 0; i < length - msg.length(); i++) {
            builder.append(" ");
        }

        return builder.toString();
    }

}
