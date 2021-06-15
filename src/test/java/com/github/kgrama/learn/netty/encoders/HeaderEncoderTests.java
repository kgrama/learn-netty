package com.github.kgrama.learn.netty.encoders;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.github.kgrama.learn.netty.support.NettyChannelTestSupport;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class HeaderEncoderTests  extends NettyChannelTestSupport {
	
	private ByteBufHeaderEncoder encoderToTest;
	private ByteBuf testByteBuffer;
	
	@BeforeEach 
	public void mockCreation() throws UnsupportedEncodingException {
		MockitoAnnotations.openMocks(this);
		encoderToTest = new ByteBufHeaderEncoder();
		testByteBuffer = Unpooled.copiedBuffer("adsvfdasfdsf asdf asdf  dasf adsf".getBytes("UTF-8"));
	}
	
	@Test
	public void verifyHeaderEncoder() {
		var outByteBuffer = Unpooled.buffer();
		assertDoesNotThrow(() -> encoderToTest.encode(mockContext, testByteBuffer, outByteBuffer));
	}
}
