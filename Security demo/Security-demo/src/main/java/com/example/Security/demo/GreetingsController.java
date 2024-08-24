package com.example.Security.demo;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {
	
	@GetMapping("/hello")
	public String sayHello() {
		return "hello";
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/user")
	public String sayHelloUser() {
		return "user";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public String sayHelloAdmin() {
		return "admin";
	}
	
	@PostAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin1")
	public String sayHelloAdmin1() {
		System.out.println("hi");
		return "admin";
	}
	

}
