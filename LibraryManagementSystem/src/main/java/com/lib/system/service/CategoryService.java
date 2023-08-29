package com.lib.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.lib.system.entity.Category;
import com.lib.system.repository.LibraryManagementSystem;

@Service
@Transactional
public class CategoryService {

	@Autowired
	LibraryManagementSystem libraryRepository;

	public List<Category> getAllCategory() {
		List<Category> pList = this.libraryRepository.getAllCategory();
		return pList;
	}

	public int getNewCatId() {
		int id = 0;
		if (this.libraryRepository.getNewCatId() == null ) {
			id = 1;
		} else {
			id = this.libraryRepository.getNewCatId().getId();
			id ++;
		}
		return id ;
	}

	// add Category
	public void addNewCategory(Category category) {
		try {
			this.libraryRepository.addCategory(category);
		} catch (Exception e) {
			TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
			System.out.print("errrrrorrr");
			System.out.print(e);
		}
	}

}
