package com.lib.system.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lib.system.entity.Book;
import com.lib.system.entity.User;

@Mapper
public interface LibraryManagementSystem {
	void save(Book book);

	void addUserData(User user);
	
	
	List<Book> getAllBook();
	
	List<Book> findByData();
}