package cn.liyu.chat_demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

/**
 * @author liyu
 * @date 2020/4/24 15:19
 * @description 服务端心跳检测Handler
 */
public class NettyServerHeartHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                System.out.println("读空闲。。。");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                System.out.println("写空闲。。。");
            } else if (event.state() == IdleState.ALL_IDLE) {
                System.out.println("超时关闭通道。。。" + ctx.channel().localAddress());
                ctx.channel().close();
            }
        }
    }
}
