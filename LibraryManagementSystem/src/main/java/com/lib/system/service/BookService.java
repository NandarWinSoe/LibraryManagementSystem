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
		}
	}
 
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
	
	public int getNewBookId() {
		int id = 0;
		if (this.libraryRepository.getNewBookId() == null ) {
			id = 1;
		} else {
			id = this.libraryRepository.getNewBookId().getId();
			id ++;
		}
		return id ;
	}

	public Book checkLendOrNot(int id) {
		return this.libraryRepository.checkLendOrNot(id);
	}

	// Update expense
	public void updateBookData(Book book) {
		try {

			this.libraryRepository.updateBookData(book);
		} catch (Exception e) {
			TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
		}
	}
	
	public Book findById(int id) {
		return this.libraryRepository.findById(id);
	}

	public void lendBook(int id, int bookId) {
		try {
			this.libraryRepository.lendBook(id, bookId);
		} catch (Exception e) {
			TransactionInterceptor.currentTransactionStatus().setRollbackOnly(); 
		}
	}

	public void returnBook(int bookId) {
		try {
			this.libraryRepository.returnBook(bookId);
		} catch (Exception e) {
			TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
		}
	}

	public List<Book> findByData(Book data) {
		List<Book> pList = new ArrayList<Book>();

//		if (data.getCategoryId() != 0 && !data.getCategoryId().equals(null)) {
//			pList = this.libraryRepository.findByCategory(data.getCategoryId());
//		} else if (!data.getAuthor().equals("") && !data.getAuthor().equals(null)) {
//			pList = this.libraryRepository.findByAuthor(data.getAuthor());
//		} else if (data.getId() != 0 && data.getId() != null) {
//			pList = this.libraryRepository.findById(data.getId());
//		} else if (!data.getName().equals("") && !data.getName().equals(null)) {
//			pList = this.libraryRepository.findByName(data.getName());
//		} else {
//			pList = this.libraryRepository.getAllBook();
//		}
		
		if ((data.getCategoryId() != 0 && !data.getCategoryId().equals(null)) || (!data.getAuthor().equals("") && !data.getAuthor().equals(null)) ||  (data.getId() != 0 && data.getId() != null)  || (!data.getName().equals("") && !data.getName().equals(null)) ) {
			pList = this.libraryRepository.findByData(data);
		}  else {
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
