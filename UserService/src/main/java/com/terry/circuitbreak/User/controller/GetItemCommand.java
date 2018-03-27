package com.terry.circuitbreak.User.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.terry.circuitbreak.Item.model.Item;
import com.terry.circuitbreak.User.model.User;

public class GetItemCommand extends HystrixCommand<List<User>>{
	String name;
	public GetItemCommand(String name) {
		super(HystrixCommandGroupKey.Factory.asKey("ItemServiceGroup"));
		this.name = name;
	}

	// GetItem command
	@Override
	protected List<User> run() throws Exception {
		List<User> usersList = new ArrayList<User>();
		
		RestTemplate restTemplate = new RestTemplate();
		List<Item> itemList = (List<Item>)restTemplate.exchange("http://localhost:8082/users/"+name+"/items"
										,HttpMethod.GET,null
										,new ParameterizedTypeReference<List<Item>>() {}).getBody();
		usersList.add(new User(name,"myemail@mygoogle.com",itemList));
				
		return usersList;
	}
	
	// fall back method
	// it returns default result
	@Override
	protected List<User> getFallback(){
		List<User> usersList = new ArrayList<User>();
		usersList.add(new User(name,"myemail@mygoogle.com"));
		
		return usersList;
	}
}
