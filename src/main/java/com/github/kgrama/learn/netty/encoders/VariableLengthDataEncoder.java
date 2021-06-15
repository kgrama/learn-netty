package com.github.kgrama.learn.netty.encoders;

import java.nio.charset.Charset;

import com.github.kgrama.learn.netty.model.RequestExemplar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class VariableLengthDataEncoder extends MessageToByteEncoder<RequestExemplar> {

	@Override
	protected void encode(ChannelHandlerContext ctx, RequestExemplar msg, ByteBuf out) throws Exception {
		System.out.println("encoding data from exemplar");
		out.writeBytes(msg.getDataForServer().getBytes(Charset.forName("UTF-8")));
	}

}
