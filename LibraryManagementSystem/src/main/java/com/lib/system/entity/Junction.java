package com.lib.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "j001")
public class Junction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer id;
	@Column(name = "userId")
	Integer userId;
	@Column(name = "userName")
	String userName;  
	@Column(name = "bookId")
	Integer bookId;
	@Column(name = "bookName")
	String bookName;
}
