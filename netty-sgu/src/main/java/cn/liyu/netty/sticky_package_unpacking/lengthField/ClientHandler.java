package cn.liyu.netty.sticky_package_unpacking.lengthField;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author liyu
 * @date 2020/7/17 10:25
 * @description
 */
public class ClientHandler extends SimpleChannelInboundHandler<User> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 3; i++) {

            ctx.writeAndFlush(getUser(i));
        }
    }

    private User getUser(int i) {
        User user = new User();
        user.setAge(i);
        user.setName("zhangxufeng");
        return user;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, User user) throws Exception {
        System.out.println("receive message from server: " + user.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}

