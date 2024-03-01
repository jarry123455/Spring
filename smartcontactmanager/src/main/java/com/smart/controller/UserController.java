package com.smart.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	
	
	@GetMapping("/index")
	public String dashboard() {
		System.out.println("Normal Dashboard");
		return "normal/dashboard";
	}
}
