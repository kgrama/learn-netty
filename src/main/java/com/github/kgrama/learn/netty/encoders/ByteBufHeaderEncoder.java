package com.github.kgrama.learn.netty.encoders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ByteBufHeaderEncoder extends MessageToByteEncoder<ByteBuf> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
		System.out.println("adding header to outbound message");
		out.writeShort(msg.readableBytes());
		out.writeBytes(msg);
	}

}
