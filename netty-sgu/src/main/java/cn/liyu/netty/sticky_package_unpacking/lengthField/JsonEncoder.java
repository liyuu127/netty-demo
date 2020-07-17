package cn.liyu.netty.sticky_package_unpacking.lengthField;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author liyu
 * @date 2020/7/17 11:48
 * @description JsonDecoder首先从接收到的数据流中读取字节数组，然后将其反序列化为一个User对象。
 */
public class JsonEncoder extends MessageToByteEncoder<User> {


    @Override
    protected void encode(ChannelHandlerContext ctx, User user, ByteBuf buf) {
        String json = JSON.toJSONString(user);
        ctx.writeAndFlush(Unpooled.wrappedBuffer(json.getBytes()));
    }
}

