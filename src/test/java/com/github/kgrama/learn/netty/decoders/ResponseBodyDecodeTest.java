package com.github.kgrama.learn.netty.decoders;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.nio.charset.Charset;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.github.kgrama.learn.netty.support.NettyChannelTestSupport;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ResponseBodyDecodeTest  extends NettyChannelTestSupport {
	
	private ByteBuf inputVal;
	
	private ResponseBodyDecoder responseBodyDecoder = new ResponseBodyDecoder();
	
	@BeforeEach
	public void mockCreation() {
		MockitoAnnotations.openMocks(this);
		inputVal = Unpooled.buffer();
	}
	
	@Test
	public void verifyDecode() throws Exception {
		var out = new ArrayList<>();
		when(mockContext.pipeline()).thenReturn(mockPipeline);
		inputVal.writeShort(4);
		inputVal.writeBytes("life".getBytes(Charset.forName("UTF-8")));
		inputVal.writeShort(42);
		assertDoesNotThrow(() -> responseBodyDecoder.decode(mockContext, inputVal, out));
		assertEquals(1, out.size());
	}
		
}
