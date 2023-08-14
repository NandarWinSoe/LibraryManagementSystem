package com.lib.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.lib.system.entity.Book;
import com.lib.system.repository.BookRepository;

public class BookService {
	@Autowired
	BookRepository bookRepository;
	// add book
	public void addData(Book data) {
		try {
			this.bookRepository.save(data);
		} catch (Exception e) {
			TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
		}
	}

}
