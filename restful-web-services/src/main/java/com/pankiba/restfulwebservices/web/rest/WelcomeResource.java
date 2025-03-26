package com.pankiba.restfulwebservices.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeResource {

	@GetMapping(path = "/")
	public String helloWorld() {
		return "Welcome to Spring Boot, world of new possibilities !!";
	}

}
