package cn.liyu.netty.sticky_package_unpacking.delimiterBasedFrame;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author liyu
 * @date 2020/7/17 10:03
 * @description
 */
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    int count = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println("s = " + msg);
        count++;
        System.out.println("count = " + count);
    }
}
