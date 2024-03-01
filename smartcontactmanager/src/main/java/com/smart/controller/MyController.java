package com.smart.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/admin")
@RestController
public class MyController {
	
	
	@GetMapping("/me")
	public String dashboard() {
		System.out.println("Hello Dashboard");
		return "This is admin page";
	}
}