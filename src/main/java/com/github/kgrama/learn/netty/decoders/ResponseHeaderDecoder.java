package com.github.kgrama.learn.netty.decoders;

import java.nio.charset.Charset;
import java.util.List;

import com.github.kgrama.learn.netty.checkpoints.VariableLengthDataCheckpoints;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

public class ResponseHeaderDecoder extends ReplayingDecoder<VariableLengthDataCheckpoints>{
	private static final String LIFE = "life";
	private final ByteToMessageDecoder followOnDecoder;
	
	public ResponseHeaderDecoder(ByteToMessageDecoder followOnDecoder) {
		super(VariableLengthDataCheckpoints.WAIT_HEADER);
		this.followOnDecoder = followOnDecoder;
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		System.out.println("Header decoder");
		if (VariableLengthDataCheckpoints.WAIT_HEADER.equals(state()) && in.readableBytes() >= 2 &&
				in.readShort() == 42) {
			checkpoint(VariableLengthDataCheckpoints.RECIEVE_HEADER);
			System.out.println("42 found");
		}
		
		if (VariableLengthDataCheckpoints.RECIEVE_HEADER.equals(state())&& in.readableBytes() >= 4 &&
			LIFE.equalsIgnoreCase(in.readCharSequence(4, Charset.forName("UTF-8")).toString())) {
			checkpoint(VariableLengthDataCheckpoints.RECIEVE_METADATA);
			System.out.println("its alive");
		}
		if (VariableLengthDataCheckpoints.RECIEVE_METADATA.equals(state()) && in.readableBytes() >= 2) {
			ctx.pipeline().addBefore("exemplarHandler","messageDecoder", followOnDecoder);
			System.out.println("Number of bytes in buffer: " + super.actualReadableBytes());
			out.add(in.readBytes(super.actualReadableBytes()));
			ctx.pipeline().remove(this);
		}
	}

}
;