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
		if (user != null && (user.getIs_super() || user.getIs_staff())) {
			String[] features = request.getRequestURI().trim().split("/");
			String feature="";
			String action="";
			if(features.length<=5) {
				feature = features[2].replace("ies", "ys");
				feature = feature.substring(0, feature.length() - 1);
				action = features.length > 3 ? features[3] : "read";
				
			}else if(features.length<=8){
				feature = features[5].replace("ies", "ys");
				feature = feature.substring(0, feature.length() - 1);
				action = features.length > 6 ? features[6] : "read";
			}else if(features.length<=11){
				feature = features[8].replace("ies", "ys");
				feature = feature.substring(0, feature.length() - 1);
				action = features.length > 9 ? features[9] : "read";
			} else {
				feature = features[11].replace("ies", "ys");
				feature = feature.substring(0, feature.length() - 1);
				action = features.length > 12 ? features[12] : "read";
			}
			if (action.equalsIgnoreCase("read") || action.equalsIgnoreCase("create") || action.equalsIgnoreCase("edit")
					|| action.equalsIgnoreCase("delete")) {
				if (!checkPermission(feature, action)) {
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
		Optional<User> optional = userService.findByUsernameIgnoreCase(user.getUsername());
		if (optional.isPresent()) {
			user = optional.get();
			for (Permission permission : user.getPermissions()) {
				for (Action item : permission.getActions()) {
					if (item.getFeature().equalsIgnoreCase(feature) && item.getCode().equalsIgnoreCase(action)) {
						System.out.println("Permission access!");
						return true;
					}
				}
			}
		}
		return false;
	}

}
