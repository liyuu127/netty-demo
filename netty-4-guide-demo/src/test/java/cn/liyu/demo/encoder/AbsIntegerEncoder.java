/**
 * Welcome to https://waylau.com
 */
package cn.liyu.demo.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Abs Integer Encoder.
 * 
 * @since 1.0.0 2020年1月4日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {
	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext,
			ByteBuf in, List<Object> out) throws Exception {
		while (in.readableBytes() >= 4) {
			int value = Math.abs(in.readInt());
			out.add(value);
		}
	}
}