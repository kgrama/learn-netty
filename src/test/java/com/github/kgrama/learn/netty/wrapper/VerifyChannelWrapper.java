package com.github.kgrama.learn.netty.wrapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.kgrama.learn.netty.exceptions.ChannelBusyException;
import com.github.kgrama.learn.netty.model.ResponseExemplar;
import com.github.kgrama.learn.netty.support.ChannelTestSupport;


public class VerifyChannelWrapper extends ChannelTestSupport {
	
	private BlockingChannelWrapper channelWrapper;
	
	@BeforeEach
	public void configureForTest() {
		configureTestPipelineAndData();
		channelWrapper = new BlockingChannelWrapper(testViewofChannel, inboundHandler);
	}
	
	@Test
	public void verifyWrapperAbleToWaitForResponse() {
		injectDelayedInboundMessage(2000);
		System.out.println("Sending request");
		ResponseExemplar responseFromRemote =  assertDoesNotThrow( () -> 
			channelWrapper.sendAndRecieveMessage(testData), "Writing data to channel should not throw error");
		Queue<Object> outboundMessages = testViewofChannel.outboundMessages();
		commonValidResponseAssertions(responseFromRemote, outboundMessages);
	}
	
	@Test
	public void verifyWrapperQueuesRequests() {
		injectDelayedInboundMessage(2500);
		enqueueFailRequest();
		System.out.println("Sending request");
		ResponseExemplar responseFromRemote =  assertDoesNotThrow( () -> 
			channelWrapper.sendAndRecieveMessage(testData), "Writing data to channel should not throw error");
		Queue<Object> outboundMessages = testViewofChannel.outboundMessages();
		commonValidResponseAssertions(responseFromRemote, outboundMessages);
	}

	private void enqueueFailRequest() {
		Runnable failRequestInjector = () -> {
			try {
				Thread.sleep(250);
				assertThrows(ChannelBusyException.class, () -> 
				channelWrapper.sendAndRecieveMessage(testData), "Writing data to busy channel should throw error");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
		new Thread(failRequestInjector).start();
	}

	private void injectDelayedInboundMessage(int sleepTimeInMillis) {
		System.out.println("Delayed addition of response");
		Runnable inboundMessageInjector = () -> {
			try {
				Thread.sleep(sleepTimeInMillis);
				testViewofChannel.writeInbound(simulatedResponse);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
		new Thread(inboundMessageInjector).start();
	}
}
