package com.example.demo.controller.admin.auth;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.domain.user.User;
import com.example.demo.model.auth.AdminLoginDto;
import com.example.demo.service.user.UserService;


@Controller
@RequestMapping("admin/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("login")
	public ModelAndView login(ModelMap model) {
		AdminLoginDto admin = new AdminLoginDto();
		model.addAttribute("admin", admin);
		return new ModelAndView("admin/auth/login", model);
	}
	
	@PostMapping("login")
	public ModelAndView login(ModelMap model,
			@Valid @ModelAttribute("admin") AdminLoginDto adminLoginDto,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/auth/login");
		}
		User user = userService.login(adminLoginDto.getUsername(), adminLoginDto.getPassword());
		if(user==null) {
			model.addAttribute("error", "Invalid username or email");
			return new ModelAndView("admin/auth/login", model);
		}
		session.setAttribute("user", user);
		Object redirectUri= session.getAttribute("redirect-uri");
		if(redirectUri!=null) {
			session.removeAttribute("redirect-uri");
			return new ModelAndView("redirect:"+redirectUri);
		}
		
		return new ModelAndView("redirect:/admin");
	}
	
}
