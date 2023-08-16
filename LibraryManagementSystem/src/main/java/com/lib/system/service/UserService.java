package com.lib.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.lib.system.entity.User;
import com.lib.system.repository.LibraryManagementSystem;

@Service
@Transactional
public class UserService {
	@Autowired
	LibraryManagementSystem libraryRepository;
	// add book
	public void addUserData(User data) {
		try {
			this.libraryRepository.addUserData(data);
		} catch (Exception e) {
			TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
			System.out.print("Userrrrrerrrrrorrr");
		}
	}
}
