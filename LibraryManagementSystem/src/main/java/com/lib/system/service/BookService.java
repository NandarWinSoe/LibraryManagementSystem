package com.lib.system.service;

import java.util.List;

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

	/*
	 * public List<Book> getAllBook(Book data) { try {
	 * this.libraryRepository.getAllBook(data); } catch (Exception e) {
	 * TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
	 * System.out.print("errrrrorrr"); } }
	 */

	// get all book
	public List<Book> getAllBook() {
		List<Book> pList = this.libraryRepository.getAllBook();
		return pList;
	}

}
