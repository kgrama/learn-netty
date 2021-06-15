package com.github.kgrama.learn.netty.support;

import org.mockito.Mock;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

public abstract class NettyChannelTestSupport {

	
	@Mock
	protected ChannelHandlerContext mockContext;
	
	@Mock
	protected ChannelPipeline mockPipeline;
	
}
