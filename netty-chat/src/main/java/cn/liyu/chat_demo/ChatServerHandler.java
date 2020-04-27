package cn.liyu.chat_demo;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义服务器端业务处理类
 *
 * @author liyu
 * @date 2020/4/24 15:42
 * @description
 */
//标示一个 ChannelHandler 可以被多个 Channel 安全地共享
@ChannelHandler.Sharable
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    public static List<Channel> channels = new ArrayList<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        channels.add(incoming);
        System.out.println("[Server]:" + incoming.remoteAddress().toString().substring(1) + "在线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        channels.remove(incoming);
        System.out.println("[Server]:" + incoming.remoteAddress().toString().substring(1) + "掉线");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            //排除当前通道
            if (channel != incoming) {
                channel.writeAndFlush("[" + incoming.remoteAddress().toString().substring(1) + "]说: " + msg + "\n");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Channel incoming = ctx.channel();
        System.out.println("[Server]:" + incoming.remoteAddress().toString().substring(1) + "异常");
        ctx.close();
    }
}
