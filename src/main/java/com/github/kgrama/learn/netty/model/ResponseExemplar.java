package com.github.kgrama.learn.netty.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseExemplar {

	@Setter
	@Getter
	private int numberA;
	
	@Setter
	@Getter
	private String stringA;
	
	@Setter
	@Getter
	private int numberB;
}
