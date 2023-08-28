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
			if (data.getAdmin() == null) {
				data.setAdmin("0");
			}else {
				data.setAdmin("1");
			}
			this.libraryRepository.addUserData(data);
		} catch (Exception e) {
			TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
			System.out.print(e);
		}
	}
	
	public User checkUser(String name,String password) {
		return this.libraryRepository.checkUser(name,password);
	}
	
	public User checkAdmin(int userId) {
		User result= new User();
		try {
			result= this.libraryRepository.checkAdmin(userId);
		}catch (Exception e) {
			TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
			System.out.print(e);
		}
		return result;
	}
}
