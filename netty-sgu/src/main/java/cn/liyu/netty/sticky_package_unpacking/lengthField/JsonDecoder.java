package cn.liyu.netty.sticky_package_unpacking.lengthField;


import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @author liyu
 * @date 2020/7/17 11:44
 * @description
 */
public class JsonDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        User user = JSON.parseObject(new String(bytes, CharsetUtil.UTF_8), User.class);
        out.add(user);
    }
}

