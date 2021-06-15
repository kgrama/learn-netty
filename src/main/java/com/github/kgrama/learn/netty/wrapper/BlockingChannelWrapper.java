package com.github.kgrama.learn.netty.wrapper;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import com.github.kgrama.learn.netty.exceptions.ChannelBusyException;
import com.github.kgrama.learn.netty.handler.ExemplarInboundHandler;
import com.github.kgrama.learn.netty.model.RequestExemplar;
import com.github.kgrama.learn.netty.model.ResponseExemplar;

import io.netty.channel.Channel;

public class BlockingChannelWrapper {

	private final Channel commsChannel;

	private final Semaphore channelManager = new Semaphore(1);

	private final ExemplarInboundHandler inboundHandler;

	public BlockingChannelWrapper(Channel channel, ExemplarInboundHandler inboundHandler) {
		this.commsChannel = channel;
		this.inboundHandler = inboundHandler;
	}

	public ResponseExemplar sendAndRecieveMessage(RequestExemplar data) throws InterruptedException{
		var threadHasLock = new Semaphore(1);
		if (channelManager.tryAcquire(2, TimeUnit.SECONDS)) {
			try {
				threadHasLock.acquire();
				inboundHandler.setChannelWrapper(threadHasLock);
				commsChannel.writeAndFlush(data);
				threadHasLock.tryAcquire(4, TimeUnit.SECONDS);
				return inboundHandler.getResponses().size() > 0 ? inboundHandler.getResponses().remove(0): null;
			} catch (Exception e) {
				System.out.println("Exception in wrapper");
				e.printStackTrace();
				return null;
			} finally{
				channelManager.release();
			}
		}
		
		throw new ChannelBusyException("Unable to acceess channel for atleast 2 seconds");
	}

}
