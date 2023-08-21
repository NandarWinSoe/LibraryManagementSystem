package com.lib.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		int id = this.libraryRepository.getNewCatId().getId();
		return id+1;
	}

}
