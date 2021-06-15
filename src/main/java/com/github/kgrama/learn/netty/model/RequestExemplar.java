package com.github.kgrama.learn.netty.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestExemplar {
	
	@Getter
	@Setter
	private String dataForServer;
}
