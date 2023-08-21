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
			// System.out.print("categoryIDDDDD"+data.getCategoryId());
			// data.setCategory(libraryRepository.findCategoryById(data.getCategoryId()));
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

	// get detail
	public Book getDetail(int id) {
		Book book = this.libraryRepository.findByIdForUpdate(id);
		return book;
	}

	// Update expense
	public void updateBookData(Book book) {
		try {

			this.libraryRepository.updateBookData(book);
		} catch (Exception e) {
			TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
		}
	}

	public List<Book> findByData(Book data) {
		List<Book> pList = new ArrayList<Book>();

		if (data.getCategoryId() != 0 && !data.getCategoryId().equals(null)) {
			pList = this.libraryRepository.findByCategory(data.getCategoryId());
		} else if (!data.getAuthor().equals("") && !data.getAuthor().equals(null)) {
			pList = this.libraryRepository.findByAuthor(data.getAuthor());
		} else if (data.getId() != 0 && data.getId() != null) {
			pList = this.libraryRepository.findById(data.getId());
		} else if (!data.getName().equals("") && !data.getName().equals(null)) {
			pList = this.libraryRepository.findByName(data.getName());
		} else {
			pList = this.libraryRepository.getAllBook();
		}

		return pList;
	}

	public List<Book> findByCategory(int id) {
		List<Book> pList = new ArrayList<Book>();
		pList = this.libraryRepository.findByCategory(id);
		return pList;
	}

	public List<Book> findByType(int id) {
		List<Book> pList = new ArrayList<Book>();
		pList = this.libraryRepository.findByType(id);
		return pList;
	}

}
