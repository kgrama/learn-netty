package com.github.kgrama.learn.netty.decoders;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.charset.Charset;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;

import com.github.kgrama.learn.netty.support.NettyChannelTestSupport;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class HeaderDecoderStatemachine extends NettyChannelTestSupport {
	
	private ByteBuf inputVal;
	
	private ResponseHeaderDecoder responseHeaderDecoder = new ResponseHeaderDecoder(new ResponseBodyDecoder());
	
	@BeforeEach
	public void mockCreation() {
		MockitoAnnotations.openMocks(this);
		inputVal = Unpooled.buffer();
	}
	
	@Test
	public void verifyCheckpoints() throws Exception {
		var out = new ArrayList<>();
		assertDoesNotThrow(() -> responseHeaderDecoder.decode(mockContext, inputVal, out));
	}
	
	@Test
	public void verifyHandOff() throws Exception {
		var out = new ArrayList<>();
		when(mockContext.pipeline()).thenReturn(mockPipeline);
		inputVal.writeShort(42);
		inputVal.writeBytes("life".getBytes(Charset.forName("UTF-8")));
		inputVal.writeShort(42);
		assertDoesNotThrow(() -> responseHeaderDecoder.decode(mockContext, inputVal, out));
		verify(mockPipeline, times(1)).addLast(ArgumentMatchers.anyString(), ArgumentMatchers.any(ResponseBodyDecoder.class));
		verify(mockPipeline, times(1)).remove(ArgumentMatchers.any(ResponseHeaderDecoder.class));
	}
}
