package cn.liyu.netty.sticky_package_unpacking.lengthField;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author liyu
 * @date 2020/7/17 10:03
 * @description
 */
public class ServerHandler extends SimpleChannelInboundHandler<User> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, User user) throws Exception {

        System.out.println("receive from client: " + user.toString());
        ctx.write(user);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
