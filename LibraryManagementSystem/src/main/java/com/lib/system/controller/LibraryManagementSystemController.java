package com.lib.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.lib.system.entity.Book;
import com.lib.system.service.BookService;
@Controller
public class LibraryManagementSystemController {
	@Autowired
	BookService bookService;
	
	@GetMapping("/")
	public String index(Model model) {
		
		return "index";
	}
	
	@GetMapping("/newBook")
	public String add(Model model) {
		model.addAttribute("form", new Book());
		return "addBook";
	}
	
	@PostMapping("/addBookData")
	public String addConfirm(Model model, @ModelAttribute("form") Book book) {
		System.out.print("Hellllll");
		this.bookService.addData(book);
		return "addBook";
	}

}
