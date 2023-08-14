package com.lib.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.lib.system.entity.Book;
import com.lib.system.repository.BookRepository;
import com.lib.system.service.BookService;

public class LibraryManagementSystemController {
	@Autowired
	BookService bookService;
	@PostMapping("/addBookData")
	public String addConfirm(Model model, @ModelAttribute("form") Book data) {
		this.bookService.addData(data);
		return "expenseDetail";
	}

}
