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
@Table(name = "user01")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer id;
	@Column(name = "name")
	String name;
	@Column(name = "email")
	String email;
	@Column(name = "password")
	String password;
	@Column(name = "phoneNo")
	String phoneNo;
	@Column(name = "admin")
	String admin;
}