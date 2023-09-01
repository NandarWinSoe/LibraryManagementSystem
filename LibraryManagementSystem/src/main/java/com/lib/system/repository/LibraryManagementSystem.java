package com.lib.system.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lib.system.entity.Book;
import com.lib.system.entity.Category;
import com.lib.system.entity.User;

@Mapper
public interface LibraryManagementSystem {
	void save(Book book);

	void addUserData(User user);
	
	User checkAdmin(int id);
	
	Book findById(int id);
	
	Book getNewBookId();
	
	List<Book> getAllBook();
	
	void updateBookData(Book book);
	
	List<Book> findByCategory(int id);
	
//	
//	List<Book> findByAuthor(String author);
//	
//	List<Book> findById(int id);
//	
	List<Book> findByData(Book book);
	
	Book findByIdForUpdate(int id);
	
	List<Book> findByName(String name);
	
	List<Book> findByType(int id);
	
	String findCategoryById(int id);
	
	List<Category> getAllCategory();
	
	Category getNewCatId();
	
	User checkUser(String name,String password);
	
	void lendBook(int lendUser, int id);
	
	void returnBook( int id);
	
	Book checkLendOrNot(int id);
	
	void addCategory(Category category);
}