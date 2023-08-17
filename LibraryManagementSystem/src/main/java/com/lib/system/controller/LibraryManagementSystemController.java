package com.lib.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.lib.system.entity.Book;
import com.lib.system.entity.User;
import com.lib.system.service.BookService;
import com.lib.system.service.UserService;
@Controller
public class LibraryManagementSystemController {
	@Autowired
	BookService bookService;
	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("bookList", this.bookService.getAllBook());
		System.out.print(this.bookService.getAllBook());
		return "index";
	}
	
	@GetMapping("/newBook")
	public String add(Model model) {
		model.addAttribute("form", new Book());
		return "addBook";
	}
	
	@PostMapping("/addBookData")
	public String addConfirm(Model model, @ModelAttribute("form") Book book) {
		this.bookService.addData(book);
		return "index";
	}
	
	@GetMapping("/getAllBook")
	public String getAllBook(Model model) {
		model.addAttribute("form", new Book());
		return "addBook";
	}
	
	
	@GetMapping("/newRegister")
	public String newRegister(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	@PostMapping("/addUserData")
	public String addUserData(Model model, @ModelAttribute("form") User user) {
		this.userService.addUserData(user);
		return "index";
	}
	
	@PostMapping("/findByData")
	public String findByData(Model model, @ModelAttribute("form") Book book) {
		this.bookService.findByData(book);
		return "index";
	}

}
