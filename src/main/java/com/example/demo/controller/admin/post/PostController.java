package com.example.demo.controller.admin.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.post.LessonService;
import com.example.demo.service.post.PostService;

@Controller
@RequestMapping
public class PostController {
	@Autowired
	private PostService postService;
	
	@Autowired
	private LessonService lessonService;
	
	
}
