package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String home(Model model) {

		model.addAttribute("title", "Home - Smart Contact Manager");
		return "home";
	}

	@GetMapping("/about")
	public String about(Model model) {

		model.addAttribute("title", "About - Contact For Any Query");
		return "about";
	}

	@GetMapping("/signup")
	public String signup(Model model) {

		model.addAttribute("title", "Register - Sign Up First");
		model.addAttribute("user", new User());
		return "signup";
	}

	// handler for registering user
	@PostMapping("/do-register")
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,
			HttpSession session) {

		try {
			if (!agreement) {
				System.out.println("Agree terms and conditions");
				throw new Exception("Agree terms and conditions");
			}

			if (bindingResult.hasErrors()) {

				System.out.println("Error " + bindingResult.toString());
				model.addAttribute("user", user);
				return "signup";
			}

			user.setRole("Role_USER");
			user.setEnabled(true);
			user.setImageUrl("default.png");

			user.setPassword(passwordEncoder.encode(user.getPassword()));

			System.out.println("User " + user);
			System.out.println("Agrrement " + agreement);

			User result = this.userRepository.save(user);

			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("Successfully Registered !! ", "alert-success"));
			return "signup";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something Went Wrong !! " + e.getMessage(), "alert-danger"));
			return "signup";
		}

	}

	
	  //handler for login
	  
	  @GetMapping("/signin") 
	  public String customLogin(Model model) {
	  model.addAttribute("title", "Login : Welcome to Login Page");
	   return "login";
	  }
	 

}
