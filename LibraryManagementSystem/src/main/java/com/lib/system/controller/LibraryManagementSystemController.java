package com.lib.system.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lib.system.entity.Book;
import com.lib.system.entity.Category;
import com.lib.system.entity.User;
import com.lib.system.service.BookService;
import com.lib.system.service.CategoryService;
import com.lib.system.service.UserService;

@Controller
public class LibraryManagementSystemController {
	@Autowired
	BookService bookService;
	@Autowired
	UserService userService;
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("bookList", this.bookService.getAllBook());
		model.addAttribute("user", new User());
		model.addAttribute("form", new Book());
		model.addAttribute("categoryList", categoryService.getAllCategory());
	//	System.out.print(this.bookService.getAllBook());
		return "index";
	}
	
	@GetMapping("validUser/{id}")
	public String validUser(Model model, @PathVariable int id) {
		model.addAttribute("bookList", this.bookService.getAllBook());
		model.addAttribute("user", new User());
		model.addAttribute("form", new Book());
		model.addAttribute("id",id);
		model.addAttribute("categoryList", categoryService.getAllCategory());
	//	System.out.print(this.bookService.getAllBook());
		return "index";
	}
	
	@GetMapping("/newBook")
	public String add(Model model) {
		model.addAttribute("form", new Book());
		model.addAttribute("categoryList", categoryService.getAllCategory());
		return "addBook";
	}
	
	@PostMapping("/addBookData")
	public String addConfirm(Model model, @ModelAttribute("form") Book book) {
		this.bookService.addData(book);
		model.addAttribute("form", new Book());
		model.addAttribute("bookList", this.bookService.getAllBook());
		model.addAttribute("categoryList", categoryService.getAllCategory());
		return "index";
	}
	
	@GetMapping("/getAllBook")
	public String getAllBook(Model model) {
		model.addAttribute("form", new Book());
		return "addBook";
	}
	
	@GetMapping("/updateBook/{id}") // read data for update
	public String update(Model model, @PathVariable int id) {
		model.addAttribute("form", this.bookService.getDetail(id));
		model.addAttribute("categoryId", this.bookService.getDetail(id).getCategoryId());
		model.addAttribute("categoryList", categoryService.getAllCategory());
		return "updateBook";
	}
	
	@PostMapping("/updateBookConfirm")
	public String updateConfirm(Model model, @ModelAttribute("form") Book book) {
		bookService.updateBookData(book);
		model.addAttribute("form", new Book());
		model.addAttribute("bookList", this.bookService.getAllBook());
		model.addAttribute("categoryList", categoryService.getAllCategory());
		return "index";
	}
	
	
	@GetMapping("/bookLend/{id}/{bookId}")
	public String bookLend(Model model, @RequestParam("id") int userId ,@RequestParam("bookId") int bookId ) {
		
	//	bookService.updateBookData(book);
		model.addAttribute("form", new Book());
		model.addAttribute("bookList", this.bookService.getAllBook());
		model.addAttribute("categoryList", categoryService.getAllCategory());
		return "index";
	}
	
	@GetMapping("/bookLend")
	public String checkLoginOrNot(Model model, @ModelAttribute("form") Book book) {
		model.addAttribute("form", new Book());
		model.addAttribute("bookList", this.bookService.getAllBook());
		model.addAttribute("categoryList", categoryService.getAllCategory());
		model.addAttribute("id", -1);
		return "index";
	}
	
	@GetMapping("/newRegister")
	public String newRegister(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	@PostMapping("/addUserData")
	public String addUserData(Model model, @ModelAttribute("form") User user) {
		this.userService.addUserData(user);
		model.addAttribute("form", new Book());
		model.addAttribute("bookList", this.bookService.getAllBook());
		model.addAttribute("categoryList", categoryService.getAllCategory());
		return "index";
	}
	
	@PostMapping("/findByData")
	public String findByData(Model model, @ModelAttribute("form") Book book) {
		if ( book.getId() == null ) {
			book.setId(0);
		}
		model.addAttribute("bookList", this.bookService.findByData(book));
		model.addAttribute("categoryList", categoryService.getAllCategory());
		return "index";
	}
	
	@GetMapping("/findByCategory")
	public String findByCategory(Model model,@RequestParam("id") int categoryId)  {
		model.addAttribute("bookList", this.bookService.findByCategory(categoryId));
		model.addAttribute("categoryList", categoryService.getAllCategory());
		model.addAttribute("form", new Book());
		return "index";
	}
	
	@GetMapping("/findByType")
	public String findByType(Model model,@RequestParam("id") int type)  {
		model.addAttribute("bookList", this.bookService.findByType(type));
		System.out.println(this.bookService.findByType(type));
		model.addAttribute("categoryList", categoryService.getAllCategory());
		model.addAttribute("form", new Book());
		return "index";
	}
	
	@GetMapping("/newCategory")
	public String addCategory(Model model) {
		model.addAttribute("form", new Category());
		model.addAttribute("id", categoryService.getNewCatId());
		return "addCategory";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("form", new User());
		return "login";
	}
	
/*	@PostMapping("/checkUser")
	public String checkUser(Model model, @ModelAttribute("form") User user) {
		User loginUser = bookService.checkUser(user.getName(),user.getPassword());
		model.addAttribute("form", new Book());
		model.addAttribute("bookList", this.bookService.getAllBook());
		model.addAttribute("categoryList", categoryService.getAllCategory());
		model.addAttribute("uId",loginUser.getId());
		model.addAttribute("uName", loginUser.getName());
		return "index";
	} */
	
//	@PostMapping("/checkUser")
//	@ResponseBody // Add this annotation to indicate that the return value should be serialized to JSON
//	public Map<String, Object> checkUser(Model model, @ModelAttribute("form") User user) {
//	    Map<String, Object> response = new HashMap<>();
//	    User loginUser = bookService.checkUser(user.getName(), user.getPassword());
//	    int id = 0;
//	    String name ="";
//	    if ( !loginUser.getId().equals(null)) {
//	    	id = loginUser.getId();
//	    	name = loginUser.getName();
//	    }
//	    // You can add user information to the response map
//	    response.put("uId", id);
//	    response.put("uName",name);
//
//	    return response;
//	}
	
	@PostMapping("/checkUser")
	@ResponseBody
	public Map<String, Object> checkUser(Model model, @ModelAttribute("form") User user) {
	    Map<String, Object> response = new HashMap<>();
	    
	    User loginUser = bookService.checkUser(user.getName(), user.getPassword());
	    
	    if (loginUser != null) {
	        int id = loginUser.getId();
	        String name = loginUser.getName();
	        
	        response.put("uId", id);
	        response.put("uName", name);
	    } else {
	        // Handle the case where loginUser is null (user not found)
	        response.put("uId", 0);
	        response.put("uName", "Invalid Name and Password");
	    }

	    return response;
	}



}
