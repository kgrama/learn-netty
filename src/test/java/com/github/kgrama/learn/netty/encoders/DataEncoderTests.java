package com.github.kgrama.learn.netty.encoders;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.github.kgrama.learn.netty.model.RequestExemplar;
import com.github.kgrama.learn.netty.support.NettyChannelTestSupport;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class DataEncoderTests extends NettyChannelTestSupport {
	
	private ByteBuf outBuf;
	
	private VariableLengthDataEncoder dataEncoder = new VariableLengthDataEncoder();
	
	@BeforeEach
	public void  setupMocks() {
		MockitoAnnotations.openMocks(this);
		outBuf = Unpooled.buffer();
	}
	
	@Test
	public void verifyByteBufferPopulated() {
		var testData = RequestExemplar.builder().dataForServer("adsfsdafsd asdfasd fasd asdf asdf asd asdf asdf asd asdf asdf asd asdfds sd a").build();
		assertDoesNotThrow(()-> dataEncoder.encode(mockContext, testData, outBuf), "There should not be an exception");
	}
}
