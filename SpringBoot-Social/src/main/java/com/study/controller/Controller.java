package com.study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@org.springframework.stereotype.Controller
public class Controller {

	@RequestMapping("/")
	public String hello1() {
		System.out.println("hello");
		return "index";
	}
	
	@ResponseBody
	@GetMapping("/hello")
	public String hello() {
		System.out.println("hello");
		return "hello";
	}
}
