package com.github.kgrama.learn.netty.decoders;

import java.nio.charset.Charset;
import java.util.List;

import com.github.kgrama.learn.netty.model.ResponseExemplar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ResponseBodyDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		var lenA = in.readShort();
		
		var outVal = ResponseExemplar.builder().numberA(lenA).stringA(in.readCharSequence(lenA, Charset.forName("UTF-8")).toString())
				.numberB(in.readShort()).build();
		out.clear();
		out.add(outVal);
	}

}
