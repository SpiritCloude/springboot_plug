package com.security.study.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.security.study.bean.User;

@Controller
public class UserController {

	@GetMapping("/")
	public String showIndex() {
		return "index";
	}
	
	@GetMapping("/authInfo")
	public String show1() {
		return "authInfo";
	}
	
	@GetMapping("/show2")
	public String show2() {
		return "show2";
	}
	
	@PreAuthorize("hasAuthority('/setsession')")
	@ResponseBody
	@GetMapping("/setsession")
	public String setSessionIntoRedis(HttpSession httpSession) {
		httpSession.setAttribute("USER_SESSION", "This is session value");
		return "session success";
	}
	
	@ResponseBody
	@GetMapping("/getsession")
	public String getSessionOnRedis(HttpSession httpSession) {
		return (String) httpSession.getAttribute("USER_SESSION");
	}
	
	@ResponseBody
	@GetMapping("/get")
	public List<User> getUser() {
		List<User> uList=new ArrayList<User>();
		
		User user1 = new User();
		user1.setUsername("哈哈");
		user1.setPassword("abc123");
		user1.setEmail("1035310879@qq.com");
		user1.setPhone("10086");
		uList.add(user1);
		
		User user2 = new User();
		user2.setUsername("tom");
		user2.setPassword("tom123");
		user2.setEmail("tom123@qq.com");
		user2.setPhone("10000");
		uList.add(user2);
		
		return uList;
	}
	
	@ResponseBody
	@PostMapping("/saveUser")
	public User saveUser(User user) {
		return user;
	}
	
	@PreAuthorize("hasAuthority('/addUser')")
	@ResponseBody
	@PostMapping("/addUser")
	public User addUser(@RequestBody User user) {
		return user;
	}
	
	@ResponseBody
	@GetMapping("/delete")
	public String deleteUser() {
		return "controller--> delete";
	}
	
	
}
