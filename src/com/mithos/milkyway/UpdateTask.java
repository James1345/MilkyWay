package com.mithos.milkyway;

import java.util.concurrent.Callable;

public class UpdateTask implements Callable<Void> {

	protected StructParticle[] bufferA, bufferB, swapBuffer;
	protected int element;
	
	public UpdateTask(StructParticle[] bufferA, StructParticle[] bufferB, int element){
		this.bufferA = bufferA;
		this.bufferB = bufferB;
		this.element = element;
		swapBuffer = null;
	}
	
	@Override
	public Void call() throws Exception {
		// TODO Update!
		
		swapBuffer = bufferA;
		bufferA = bufferB;
		bufferB = swapBuffer;
		return null;
	}

}
