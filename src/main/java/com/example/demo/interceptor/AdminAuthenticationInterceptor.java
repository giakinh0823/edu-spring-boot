package com.example.demo.interceptor;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.domain.user.Action;
import com.example.demo.domain.user.Permission;
import com.example.demo.domain.user.User;
import com.example.demo.service.user.UserService;

@Component
public class AdminAuthenticationInterceptor implements HandlerInterceptor {

	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		User user = (User) session.getAttribute("user");
		if(user!=null && (user.getIs_super() || user.getIs_staff())) {
			String[] features = request.getRequestURI().trim().split("/"); 
			String feature = features[2].substring(0, features[2].length()-1);
			String action = features.length>3 ? features[3] : "read";
			if(action.equalsIgnoreCase("read") || action.equalsIgnoreCase("create") || action.equalsIgnoreCase("edit")|| action.equalsIgnoreCase("delete")) {
				if(!checkPermission(feature, action)) {
					response.getWriter().println("Access denied!");
				}
				return checkPermission(feature, action);
			}
			return true;
		}
		session.setAttribute("redirect-uri", request.getRequestURI());
		response.sendRedirect("/admin/auth/login");
		return false;
	}
	
	private boolean checkPermission(String feature, String action) {
		User user = (User) session.getAttribute("user");
		Optional<User>  optional = userService.findByUsernameIgnoreCase(user.getUsername());
		if(optional.isPresent()) {
			user = optional.get();
			for (Permission permission : user.getPermissions()) {
				for (Action item : permission.getActions()) {
					if(item.getFeature().equalsIgnoreCase(feature) && item.getCode().equalsIgnoreCase(action)) {
						System.out.println("Permission access!");
						return true;
					}
				}
			}
		}
		return false;
	}

}
