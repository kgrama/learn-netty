package com.github.kgrama.learn.netty.exceptions;

public class ChannelBusyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7551092368614064404L;
	
	public ChannelBusyException(String message) {
		super(message);
	}
	
	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
