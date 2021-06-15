package com.github.kgrama.learn.netty.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import com.github.kgrama.learn.netty.model.ResponseExemplar;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Getter;
import lombok.Setter;


public class ExemplarInboundHandler extends ChannelInboundHandlerAdapter {

	public ExemplarInboundHandler() {
		this.channelLock = new Semaphore(1);
		channelLock.tryAcquire();
	}

	private final Semaphore channelLock ;

	@Getter
	private final List<ResponseExemplar> responses = new ArrayList<>(1);
	
	@Setter
	private Semaphore channelWrapper;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("recieved response on channel of type: " + msg.getClass().getCanonicalName());
		if (ResponseExemplar.class.isInstance(msg)) {
			System.out.println("added to responses list");
			responses.add(ResponseExemplar.class.cast(msg));
			if(channelWrapper != null) {
				channelWrapper.release();
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
