package cn.liyu.basic_demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @author liyu
 * @date 2020/4/24 14:41
 * @description 服务器端业务处理Handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 通道就绪事件
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ctx = " + ctx);
        System.out.println("NettyServerHandler 通道就绪");
    }

    /**
     * 读取数据事件
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(" Server:ctx = " + ctx);
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(" 客户端发来的信息： " + byteBuf.toString(StandardCharsets.UTF_8));
    }

    /**
     * 读取数据完毕事件
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("s数据读取完毕", StandardCharsets.UTF_8));
    }

    /**
     * 异常发生事件
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
