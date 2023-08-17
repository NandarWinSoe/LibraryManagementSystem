package com.lib.system.service;

import java.util.ArrayList;
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

	public List<Book> findByData(Book data) {
		List<Book> pList = new ArrayList<Book>();
		if (!data.getCategory().equals("")) {
			pList = this.libraryRepository.findByCategory();
		} else if (!data.getAuthor().equals("")) {
			pList = this.libraryRepository.findByAuthor();
		} else if (data.getId() != 0) {
			pList = this.libraryRepository.findById();
		} else if (!data.getName().equals("")) {
			pList = this.libraryRepository.findByName();
		}

		return pList;
	}

}
