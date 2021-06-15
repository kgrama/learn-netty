package com.github.kgrama.learn.netty.checkpoints;

public enum VariableLengthDataCheckpoints {
	RECIEVE_HEADER,
	RECIEVE_METADATA,
	RECIEVE_PAYLOAD_SIZE, WAIT_HEADER;
}
