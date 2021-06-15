package com.github.kgrama.learn.netty.channel;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.charset.Charset;
import java.util.Queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.kgrama.learn.netty.support.ChannelTestSupport;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

public class VerifyChannelEncodeAndDecode extends ChannelTestSupport {
	
	private Channel testChannel;
	
	@BeforeEach
	public void configureForTest() {
		configureTestPipelineAndData();
		testChannel = testViewofChannel;
	}
	
	@Test
	public void verifyChannelWriteUsingChannel() {
		assertDoesNotThrow( () -> 
			testChannel.writeAndFlush(testData), "Writing data to channel should not throw error");
		Queue<Object> outboundMessages = testViewofChannel.outboundMessages();
		assertFalse(outboundMessages.isEmpty());
		assertEquals(1, outboundMessages.size(), "One message in outbound queue");
		ByteBuf outBoundMessage = ByteBuf.class.cast(outboundMessages.peek());
		assertEquals(testData.getDataForServer().getBytes(Charset.forName("UTF-8")).length + 2, outBoundMessage.readableBytes(), "Vaild buffer should have length of data + 2 bytes" );
	}
	
	
	@Test
	public void verifyChannelRead() {
		testViewofChannel.writeInbound(simulatedResponse);
		assertFalse(inboundHandler.getResponses().isEmpty());
	}
}
