package com.terry.msa.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class CommandHelloWorld extends HystrixCommand<String>{
	private String name;
	
	CommandHelloWorld(String name){
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
		this.name = name;
	}
	
	@Override
	protected String run() {
		return "Hello" + name +"!";
	}
	
	@Test
	public void givenInputBobAndDefaultSettings_whenCommandExecuted_thenReturnHelloBob(){
		CommandHelloWorld helloWorldCommand = new CommandHelloWorld("World");
		assertEquals("Hello World", helloWorldCommand.execute());
	}

}
