package com.lib.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.lib.system.entity.Book;
import com.lib.system.repository.LibraryManagementSystem;
@Service
@Transactional
public class BookService {
	@Autowired
	LibraryManagementSystem libraryRepository;
	// add book
	public void addData(Book data) {
		try {
			this.libraryRepository.save(data);
		} catch (Exception e) {
			TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
			System.out.print("errrrrorrr");
		}
	}

}