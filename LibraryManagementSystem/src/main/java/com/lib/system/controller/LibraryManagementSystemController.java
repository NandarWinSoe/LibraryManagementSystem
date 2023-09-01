package com.lib.system.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.lib.system.entity.Book;
import com.lib.system.entity.Category;
import com.lib.system.entity.User;
import com.lib.system.service.BookService;
import com.lib.system.service.CategoryService;
import com.lib.system.service.UserService;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

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
		model.addAttribute("userId", 0);
		model.addAttribute("categoryList", categoryService.getAllCategory());
		// System.out.print(this.bookService.getAllBook());
		return "index";
	}

	@GetMapping("/main")
	public String index(Model model, @RequestParam("userId") int userId) {
		model.addAttribute("bookList", this.bookService.getAllBook());
		model.addAttribute("user", new User());
		model.addAttribute("form", new Book());
		model.addAttribute("userId", userId);
		model.addAttribute("categoryList", categoryService.getAllCategory());
		// System.out.print(this.bookService.getAllBook());
		return "index";
	}

	@GetMapping("validUser/{id}")
	public String validUser(Model model, @PathVariable int id) {
		model.addAttribute("bookList", this.bookService.getAllBook());
		model.addAttribute("user", new User());
		model.addAttribute("form", new Book());
		model.addAttribute("userId", id);
		model.addAttribute("categoryList", categoryService.getAllCategory());
		return "redirect:/main?userId=" + id;
	}

	@GetMapping("/newBook")
	public String add(Model model, @RequestParam("userId") int userId) {
		
		Book book = new Book();
		book.setId(bookService.getNewBookId());
		model.addAttribute("admin", userService.checkAdmin(userId).getAdmin());
		model.addAttribute("userId", userId);
		if (userService.checkAdmin(userId).getAdmin().equals("1")) {
			model.addAttribute("form", book);
			model.addAttribute("categoryList", categoryService.getAllCategory());
			return "addBook";
		} else {
			model.addAttribute("form", new Book());
			model.addAttribute("userId", userId);
			model.addAttribute("bookList", this.bookService.getAllBook());
			model.addAttribute("categoryList", categoryService.getAllCategory());
			return "index";
		}

	}

	@PostMapping("/addBookData")
	public String addConfirm(Model model, @RequestParam("fileName") MultipartFile file,
			@ModelAttribute("form") Book book, @RequestParam("userId") int userId) throws IOException {
		book.setLendUser(0);

		if (book.getBookType() == 1) {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());

			book.setFile(fileName);
			book.setSize(file.getBytes());

			// Document doc = documentRepository.save(book);
			String uploadDir = "./pdf-files/" + book.getId();
			Path uploadPath = Paths.get(uploadDir);

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			try (InputStream inputStream = file.getInputStream()) {
				Path filePath = uploadPath.resolve(fileName);
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new IOException("Could not save uploaded File: " + fileName);
			}
		}
		this.bookService.addData(book);
		model.addAttribute("userId", userId);
		model.addAttribute("form", new Book());
		model.addAttribute("bookList", this.bookService.getAllBook());
		model.addAttribute("categoryList", categoryService.getAllCategory());
		model.addAttribute("userId", userId);
		return "index"; // Return to the upload form page

	}

	@GetMapping("/download/{id}")
	public void downloadFile(@PathVariable int id, HttpServletResponse response) throws Exception {
		Book b = bookService.findById(id);
		if (b.equals(null)) {
			throw new Exception("Could not find document with ID: " + id);
		}
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename = " + b.getPdfFilePath();

		response.setHeader(headerKey, headerValue);

		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(b.getSize());
		outputStream.close();
	}

	@GetMapping("/getAllBook")
	public String getAllBook(Model model) {
		model.addAttribute("form", new Book());
		return "addBook";
	}

	@GetMapping("/updateBook/{id}/{userId}") // read data for update
	public String update(Model model, @PathVariable int id, @PathVariable int userId) {
		model.addAttribute("userId", userId);
		if (userService.checkAdmin(userId).getAdmin().equals("1")) {
			model.addAttribute("form", this.bookService.getDetail(id));
			model.addAttribute("categoryId", this.bookService.getDetail(id).getCategoryId());
			model.addAttribute("categoryList", categoryService.getAllCategory());
			return "updateBook";
		} else {
			model.addAttribute("form", new Book());
			model.addAttribute("bookList", this.bookService.getAllBook());
			model.addAttribute("categoryList", categoryService.getAllCategory());
			return "redirect:/main?userId=" + userId;
		}

	}

	@PostMapping("/updateBookConfirm")
	public String updateConfirm(Model model, @ModelAttribute("form") Book book, @RequestParam("userId") int userId) {
		bookService.updateBookData(book);
		model.addAttribute("userId", userId);
		model.addAttribute("form", new Book());
		model.addAttribute("bookList", this.bookService.getAllBook());
		model.addAttribute("categoryList", categoryService.getAllCategory());
		return "index";
	}

	@GetMapping("/bookLend/{id}/{bookId}")
	public String bookLend(Model model, @PathVariable int id, @PathVariable int bookId) {
		if (bookService.checkLendOrNot(bookId).getLendUser() == 0) { // can lend
			bookService.lendBook(id, bookId);
		} else {
			if (bookService.checkLendOrNot(bookId).getLendUser() == id) { // return
				bookService.returnBook(bookId);
			} else { // other person lend
				// nothing
			}
		}
		model.addAttribute("form", new Book());
		model.addAttribute("userId", id);
		model.addAttribute("bookList", this.bookService.getAllBook());
		model.addAttribute("categoryList", categoryService.getAllCategory());
		return "redirect:/main?userId=" + id;
	}

	@GetMapping("/bookLend")
	public String checkLoginOrNot(Model model, @ModelAttribute("form") Book book) {
		model.addAttribute("form", new Book());
		model.addAttribute("bookList", this.bookService.getAllBook());
		model.addAttribute("categoryList", categoryService.getAllCategory());
		model.addAttribute("userId", 0);
		return "redirect:/";
	}

	@GetMapping("/newRegister")
	public String newRegister(Model model, @RequestParam("userId") int userId) {
		// return "register";
		model.addAttribute("user", new User());
		model.addAttribute("userId", userId);
		// model.addAttribute("admin", userService.checkAdmin(userId).getAdmin());
//		if (userService.checkAdmin(userId).getAdmin().equals("1")) {
		model.addAttribute("form", new Book());
		model.addAttribute("categoryList", categoryService.getAllCategory());
		return "register";
//		} else {
//			model.addAttribute("form", new Book());
//			model.addAttribute("bookList", this.bookService.getAllBook());
//			model.addAttribute("categoryList", categoryService.getAllCategory());
//			return "index";
//		}
	}

	@PostMapping("/addUserData")
	public String addUserData(Model model, @ModelAttribute("form") User user, @RequestParam("userId") int userId) {
		this.userService.addUserData(user);
		model.addAttribute("form", new Book());
		model.addAttribute("userId", userId);
		model.addAttribute("bookList", this.bookService.getAllBook());
		model.addAttribute("categoryList", categoryService.getAllCategory());
		return "index";
	}

	@PostMapping("/findByData")
	public String findByData(Model model, @ModelAttribute("form") Book book, @RequestParam("userId") int userId) {
		if (book.getId() == null) {
			book.setId(0);
		}
		model.addAttribute("id", book.getId());
		model.addAttribute("userId", userId);
		model.addAttribute("bookList", this.bookService.findByData(book));
		model.addAttribute("categoryList", categoryService.getAllCategory());
		return "index";
	}

	@GetMapping("/findByCategory")
	public String findByCategory(Model model, @RequestParam("id") int categoryId, @RequestParam("userId") int userId) {
		model.addAttribute("bookList", this.bookService.findByCategory(categoryId));
		model.addAttribute("categoryList", categoryService.getAllCategory());
		model.addAttribute("form", new Book());
		model.addAttribute("userId", userId);
		return "index";
	}

	@GetMapping("/findByType")
	public String findByType(Model model, @RequestParam("id") int type, @RequestParam("userId") int userId) {
		model.addAttribute("bookList", this.bookService.findByType(type));
		// System.out.println(this.bookService.findByType(type));
		model.addAttribute("categoryList", categoryService.getAllCategory());
		model.addAttribute("form", new Book());
		model.addAttribute("userId", userId);
		return "index";
	}

	@GetMapping("/newCategory")
	public String addCategory(Model model, @RequestParam("userId") int userId) {
		Category ca = new Category();
		model.addAttribute("admin", userService.checkAdmin(userId).getAdmin());
		model.addAttribute("userId", userId);
		if (userService.checkAdmin(userId).getAdmin().equals("1")) {
			ca.setId(categoryService.getNewCatId());
			model.addAttribute("form", ca);
			return "addCategory";
		} else {
			model.addAttribute("form", new Book());
			model.addAttribute("bookList", this.bookService.getAllBook());
			model.addAttribute("categoryList", categoryService.getAllCategory());
			return "index";
		}
	}

	@PostMapping("/addNewCategory")
	public String addNewCategory(Model model, @ModelAttribute("form") Category category,
			@RequestParam("userId") int userId) {
		this.categoryService.addNewCategory(category);
		model.addAttribute("form", new Book());
		model.addAttribute("bookList", this.bookService.getAllBook());
		model.addAttribute("userId", userId);
		model.addAttribute("categoryList", categoryService.getAllCategory());
		return "index";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("form", new User());
		return "login";
	}

	@PostMapping("/checkUser")
	@ResponseBody
	public Map<String, Object> checkUser(Model model, @ModelAttribute("form") User user) {
		Map<String, Object> response = new HashMap<>();

		User loginUser = userService.checkUser(user.getName(), user.getPassword());

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
