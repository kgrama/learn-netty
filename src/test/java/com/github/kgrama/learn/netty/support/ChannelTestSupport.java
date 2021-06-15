package com.github.kgrama.learn.netty.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.charset.Charset;
import java.util.Queue;

import com.github.kgrama.learn.netty.decoders.ResponseBodyDecoder;
import com.github.kgrama.learn.netty.decoders.ResponseHeaderDecoder;
import com.github.kgrama.learn.netty.encoders.ByteBufHeaderEncoder;
import com.github.kgrama.learn.netty.encoders.VariableLengthDataEncoder;
import com.github.kgrama.learn.netty.handler.ExemplarInboundHandler;
import com.github.kgrama.learn.netty.model.RequestExemplar;
import com.github.kgrama.learn.netty.model.ResponseExemplar;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

public class ChannelTestSupport {

	
	protected EmbeddedChannel testViewofChannel;
	protected RequestExemplar testData;
	protected ExemplarInboundHandler inboundHandler;
	protected ByteBuf simulatedResponse;
	
	public ChannelTestSupport() {
		super();
	}

	protected void configureTestPipelineAndData() {
		testData = RequestExemplar.builder().dataForServer("adsfsdafsd asdfasd fasd asdf asdf asd asdf asdf asd asdf asdf asd asdfds sd a").build();
		inboundHandler = new ExemplarInboundHandler();
		testViewofChannel = new EmbeddedChannel();
		testViewofChannel.pipeline()
			.addLast(new ByteBufHeaderEncoder())
			.addLast(new VariableLengthDataEncoder())
			.addLast(new ResponseHeaderDecoder(new ResponseBodyDecoder()))
			.addLast("exemplarHandler", inboundHandler);
		simulatedResponse = Unpooled.buffer();
		simulatedResponse.writeShort(42);
		simulatedResponse.writeBytes("life".getBytes(Charset.forName("UTF-8")));
		simulatedResponse.writeShort(13);
		simulatedResponse.writeBytes("life/universe".getBytes(Charset.forName("UTF-8")));
		simulatedResponse.writeShort(5);
	}

	protected void commonValidResponseAssertions(ResponseExemplar responseFromRemote, Queue<Object> outboundMessages) {
		assertFalse(outboundMessages.isEmpty());
		assertEquals(1, outboundMessages.size(), "One message in outbound queue");
		assertNotNull(responseFromRemote);
	}

}