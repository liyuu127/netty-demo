package cn.liyu.netty.sticky_package_unpacking.delimiterBasedFrame;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author liyu
 * @date 2020/7/17 10:25
 * @description
 */
public class ClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("msg = " + msg);
    }
}
