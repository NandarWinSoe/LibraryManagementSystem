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
	
	List<Book> getAllBook();
	
	void updateBookData(Book book);
	
	List<Book> findByCategory(int id);
	
	List<Book> findByAuthor(String author);
	
	List<Book> findById(int id);
	
	Book findByIdForUpdate(int id);
	
	List<Book> findByName(String name);
	
	List<Book> findByType(int id);
	
	String findCategoryById(int id);
	
	List<Category> getAllCategory();
}